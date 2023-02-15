package com.Bibo.gateway.filter;

import com.Bibo.common.util.SqLinjectionRuleUtils;
import com.Bibo.common.util.WebfluxResponseUtil;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
@Component
@RefreshScope
public class SqLinjectionFilter implements GlobalFilter, Ordered {

    private String[] sqlinjectionHttpUrls;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // grab configuration from Config object
       // //logger.debug("----自定义防sql注入网关全局过滤器生效----");
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpMethod method = serverHttpRequest.getMethod();
        String contentType = serverHttpRequest.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        URI uri = exchange.getRequest().getURI();

        //1.动态刷新 sql注入的过滤的路径
        String path = serverHttpRequest.getURI().getRawPath();
        String matchUrls[] = this.getSqlinjectionHttpUrls();

//        if (AuthUtils.isMatchPath(path, matchUrls)) {
//            ////logger.error("请求【{}】在sql注入过滤白名单中，直接放行", path);
//            return chain.filter(exchange);
//        }

        Boolean postFlag = (method == HttpMethod.POST || method == HttpMethod.PUT) &&
                (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType) || MediaType.APPLICATION_JSON_VALUE.equals(contentType));

        //过滤get请求
        if (method == HttpMethod.GET) {

            String rawQuery = uri.getRawQuery();
            if (StringUtils.isBlank(rawQuery)) {
                return chain.filter(exchange);
            }

            ////logger.debug("请求参数为：{}", rawQuery);
            // 执行sql注入校验清理
            boolean chkRet = false;
            try {
                chkRet = SqLinjectionRuleUtils.getRequestSqlKeyWordsCheck(rawQuery);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //    如果存在sql注入,直接拦截请求
            if (chkRet) {
                ////logger.error("请求【" + uri.getRawPath() + uri.getRawQuery() + "】参数中包含不允许sql的关键词, 请求拒绝");
                return setUnauthorizedResponse(exchange);
            }
            //透传参数，不对参数做任何处理
            return chain.filter(exchange);
        }
        //post请求时，如果是文件上传之类的请求，不修改请求消息体
        else if (postFlag) {

            return DataBufferUtils.join(serverHttpRequest.getBody()).flatMap(d -> Mono.just(Optional.of(d))).defaultIfEmpty(
                    Optional.empty())
                    .flatMap(optional -> {
                        // 取出body中的参数
                        String bodyString = "";
                        if (optional.isPresent()) {
                            byte[] oldBytes = new byte[optional.get().readableByteCount()];
                            optional.get().read(oldBytes);
                            bodyString = new String(oldBytes, StandardCharsets.UTF_8);
                        }
                        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();

                        //logger.debug("{} - [{}] 请求参数：{}", method, uri.getPath(), bodyString);
                        boolean chkRet = false;
                        if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
                            //如果MediaType是json才执行json方式验证
                            chkRet = SqLinjectionRuleUtils.postRequestSqlKeyWordsCheck(bodyString);
                        } else {
                            //form表单方式，需要走get请求
                            try {
                                chkRet = SqLinjectionRuleUtils.getRequestSqlKeyWordsCheck(bodyString);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        //  如果存在sql注入,直接拦截请求
                        if (chkRet) {
                            //logger.error("{} - [{}] 参数：{}, 包含不允许sql的关键词，请求拒绝", method, uri.getPath(), bodyString);
                            return setUnauthorizedResponse(exchange);
                        }

                        ServerHttpRequest newRequest = serverHttpRequest.mutate().uri(uri).build();

                        // 重新构造body
                        byte[] newBytes = bodyString.getBytes(StandardCharsets.UTF_8);
                        DataBuffer bodyDataBuffer = toDataBuffer(newBytes);
                        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

                        // 重新构造header
                        HttpHeaders headers = new HttpHeaders();
                        headers.putAll(httpHeaders);
                        // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
                        int length = newBytes.length;
                        headers.remove(HttpHeaders.CONTENT_LENGTH);
                        headers.setContentLength(length);
                        headers.set(HttpHeaders.CONTENT_TYPE, contentType);
                        // 重写ServerHttpRequestDecorator，修改了body和header，重写getBody和getHeaders方法
                        newRequest = new ServerHttpRequestDecorator(newRequest) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return bodyFlux;
                            }

                            @Override
                            public HttpHeaders getHeaders() {
                                return headers;
                            }
                        };

                        return chain.filter(exchange.mutate().request(newRequest).build());
                    });
        } else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 设置403拦截状态
     */
    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange) {
        return WebfluxResponseUtil.responseFailed(exchange, HttpStatus.FORBIDDEN.value(),
                "request is forbidden, SQL keywords are not allowed in the parameters.");
    }

    /**
     * 字节数组转DataBuffer
     *
     * @param bytes 字节数组
     * @return DataBuffer
     */
    private DataBuffer toDataBuffer(byte[] bytes) {
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    public String[] getSqlinjectionHttpUrls() {
        return sqlinjectionHttpUrls;
    }

    public void setSqlinjectionHttpUrls(String[] sqlinjectionHttpUrls) {
        this.sqlinjectionHttpUrls = sqlinjectionHttpUrls;
    }
}
