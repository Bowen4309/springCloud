package com.Bibo.gateway.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.gateway.constant.UrlEnum;
import com.Bibo.gateway.utils.HttpRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: CGF
 * @CreateTime: 2021/8/13 14:37
 * @Description:
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        ServerHttpResponse response = exchange.getResponse();

        //内部服务接口，不允许外部访问
        if(antPathMatcher.match("/**/loginUser", path)
                ||antPathMatcher.match("/**/api-docs", path)
                ||antPathMatcher.match("/**/business/**",path) ){
            return chain.filter(exchange);
        }
        //校验用户必须登录
        if(antPathMatcher.match("/**", path)) {

            String APPTOKEN  = request.getHeaders().getFirst("RZZX-APPTOKEN");
            String USERTOKEN = request.getHeaders().getFirst("RZZX-USERTOKEN");
            String KEY_TOKEN = request.getHeaders().getFirst("KEY-USERTOKEN");

            //String res = HttpRequestUtil.doGet(UrlEnum.CHECK_USER_URL+"?userToken="+USERTOKEN);
           // System.out.println("用户令牌检验:"+res);

            //System.out.println(USERTOKEN);
            System.out.println("APPTOKEN"+APPTOKEN+"------------------------------USERTOKEN="+USERTOKEN);

            if(StringUtils.isEmpty(USERTOKEN)&&StringUtils.isEmpty(KEY_TOKEN)){
                return out(response);
            }

            //apptoken不为空则是数字证书登陆或者扫码登陆
            if(StringUtils.isNotEmpty(APPTOKEN)){
                LoginUser loginUser = RedisUtil.checkUser(USERTOKEN);
                //存放用户信息到redis
                if(null == loginUser){
                    String responses = HttpRequestUtil.doGet(UrlEnum.GET_LOGIN_USER_URL+"?userToken="+USERTOKEN);
                    System.out.println("获取登陆用户信息:"+responses);
                    JSONObject loginObject =JSONObject.parseObject(responses);
                    //判断接口获取登陆用户信息成功
                    if(loginObject.getString("status").equals("0000")){
                        try {
                            //获取用户信息
                            JSONObject loginUserObject = JSONObject.parseObject(loginObject.getString("result"));
                            System.out.println("-------------------pid="+loginUserObject.getString("pid"));
                            String userDataResponse = HttpRequestUtil.doApiPost(UrlEnum.GET_USER_INFO_URL+"?ids="+loginUserObject.getString("pid"),null);
                            System.out.println("-------------------------用户详细数据="+userDataResponse);
                            //用户详情信息查询数据
                            JSONObject userInfo = JSONObject.parseObject(userDataResponse);
                            //判断是否查询成功
                            if(userInfo.getString("status").equals("0000")){
                                JSONObject resultObj = JSONObject.parseObject(userInfo.getString("result"));
                                System.out.println("---------------------result"+resultObj.toString()+"--------------------------rows"+resultObj.getString("rows"));
                                JSONArray rows =  JSONArray.parseArray(resultObj.getString("rows"));
                                if(null != rows && rows.size()>0){
                                    System.out.println("用户信息："+rows.get(0).toString());
                                     JSONObject userObject = JSONObject.parseObject(rows.get(0).toString());
                                     loginUser = new LoginUser();
                                     loginUser.setPoliceCode(userObject.getString("policeNo"));
                                     RedisUtil.setUser(loginUser,USERTOKEN);
                                    System.out.println("存放的redis数据+"+RedisUtil.checkUser(USERTOKEN));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(null != RedisUtil.checkUser(USERTOKEN)){
                // TODO 将token信息存放在请求header中传递给下游业务
                ServerHttpRequest.Builder mutate = request.mutate();
                mutate.header("RZZX-USERTOKEN", USERTOKEN);
                ServerHttpRequest buildReuqest = mutate.build();
                System.out.println("通过网关");
                return chain.filter(exchange.mutate()
                        .request(buildReuqest)
                        .response(response)
                        .build());
            }else if(null != RedisUtil.checkKey(KEY_TOKEN)){
                if(antPathMatcher.match("/trafficBusinessApi/**", path)){
                    // TODO 将token信息存放在请求header中传递给下游业务
                    ServerHttpRequest.Builder mutate = request.mutate();
                    mutate.header("KEY-USERTOKEN", KEY_TOKEN);
                    ServerHttpRequest buildReuqest = mutate.build();
                    System.out.println("通过网关");
                    return chain.filter(exchange.mutate()
                            .request(buildReuqest)
                            .response(response)
                            .build());
                }
            }else{
                return out(response);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {
        JSONObject message = new JSONObject();
        message.put("success", false);
        message.put("code", 28004);
        message.put("data", "鉴权失败");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
