package com.Bibo.common.util;


import com.alibaba.fastjson.JSONObject;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.constant.CrashTableTypeEnum;
import com.Bibo.common.constant.ServiceUrlEnum;
import com.Bibo.common.pojo.vo.CarGPSDataResultVO;
import com.Bibo.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
public class HttpRequestUtil {

    /**
     * httpClient的get请求方式
     * 使用GetMethod来访问一个URL对应的网页实现步骤：
     * 1.生成一个HttpClient对象并设置相应的参数；
     * 2.生成一个GetMethod对象并设置响应的参数；
     * 3.用HttpClient生成的对象来执行GetMethod生成的Get方法；
     * 4.处理响应状态码；
     * 5.若响应正常，处理HTTP响应内容；
     * 6.释放连接。
     *
     * @param url
     * @param charset
     * @return
     */
    public static String doGet(String url, String charset,String tokenUrl) throws IOException {
        String accessToken = getGetewayToken(tokenUrl);

        //1.生成HttpClient对象并设置参数
        HttpClient httpClient = new HttpClient();
        //设置Http连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        //2.生成GetMethod对象并设置参数
        GetMethod getMethod = new GetMethod(url);
        //设置get请求超时为5秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        //设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        if (StringUtils.isNotEmpty(accessToken)) {
            getMethod.addRequestHeader("Authorization", accessToken);
        }
        String response = "";
        //3.执行HTTP GET 请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            //4.判断访问的状态码
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("请求出错：" + getMethod.getStatusLine());
            }
            //5.处理HTTP响应内容
            //HTTP响应头部信息，这里简单打印
            Header[] headers = getMethod.getResponseHeaders();
            for (Header h : headers) {
                System.out.println(h.getName() + "---------------" + h.getValue());
            }
            //读取HTTP响应内容，这里简单打印网页内容
            //读取为字节数组
            byte[] responseBody = getMethod.getResponseBody();
            response = new String(responseBody, charset);
            //System.out.println("-----------response:" + response);
            //读取为InputStream，在网页内容数据量大时候推荐使用
            //InputStream response = getMethod.getResponseBodyAsStream();
        } catch (HttpException e) {
            //发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            //发生网络异常
            System.out.println("发生网络异常!");
        } finally {
            //6.释放连接
            getMethod.releaseConnection();
        }
        return response;
    }

    /**
     * post请求
     *
     * @param url
     * @param
     * @return
     */
    public static String doApiPost(String url, String body, String token) throws IOException {
        HttpClient httpClient = new HttpClient();
        Protocol https = new Protocol("https", new HTTPSSecurePotocolocketFactory(), 443);
        Protocol.registerProtocol("https", https);
        PostMethod postMethod = new PostMethod(url);

        // postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
        postMethod.addRequestHeader("accept", "*/*");
        postMethod.addRequestHeader("connection", "Keep-Alive");
        //设置json格式传送
        postMethod.addRequestHeader("Content-Type", "application/json;charset=utf-8");
        //必须设置下面这个Header
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        //添加请求参数
        // postMethod.addParameter("commentId", json.getString("commentId"));
        if (StringUtils.isNotEmpty(token)) {
            postMethod.addRequestHeader("x-token", token);
        }

        StringBuilder stringBuilder = new StringBuilder();

        if(StringUtils.isNotBlank(body)){
            StringRequestEntity entity = new StringRequestEntity(body, "application/json", "UTF-8");
            postMethod.setRequestEntity(entity);
        }

        int code = httpClient.executeMethod(postMethod);

       // System.out.println(code+"  RequestBody ->"+ body +  " ||  ResponseString ->" +postMethod.getResponseBodyAsString());
        Protocol.unregisterProtocol("https");
        if (code == 200) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }
            log.info("httpRequest URL->" + url);
            log.info("httpRequest BODY->" + body);
            log.info("httpRequest CODE->{}   RESPONSE->{}", code, stringBuilder.toString());
        }else{
            log.info("httpRequest URL->" + url);
            log.info("httpRequest BODY->" + body);
            log.info("天河接口调用失败;code:"+code);
            log.info("httpRequest CODE->{}   RESPONSE->{}", code, stringBuilder.toString());
        }
        return stringBuilder.toString();
    }

    /**
     * post请求
     *
     * @param url
     * @param
     * @return
     */
    public static String doApiGet(String url, String tokenName,String tokenValue) throws IOException {
        HttpClient httpClient = new HttpClient();
        Protocol https = new Protocol("https", new HTTPSSecurePotocolocketFactory(), 443);
        Protocol.registerProtocol("https", https);
        GetMethod getMethod = new GetMethod(url);

        // postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
        getMethod.addRequestHeader("accept", "*/*");
        getMethod.addRequestHeader("connection", "Keep-Alive");
        //设置json格式传送
        getMethod.addRequestHeader("Content-Type", "application/json;charset=utf-8");
        //必须设置下面这个Header
        getMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        //添加请求参数
        // postMethod.addParameter("commentId", json.getString("commentId"));
        if (StringUtils.isNotEmpty(tokenName)) {
            getMethod.addRequestHeader(tokenName, tokenValue);
        }

        StringBuilder stringBuilder = new StringBuilder();

        int code = httpClient.executeMethod(getMethod);

        // System.out.println(code+"  RequestBody ->"+ body +  " ||  ResponseString ->" +postMethod.getResponseBodyAsString());
        Protocol.unregisterProtocol("https");
        if (code == 200) {
            InputStream inputStream = getMethod.getResponseBodyAsStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }
//            log.info("httpRequest URL->" + url);
//            log.info("httpRequest CODE->{}   RESPONSE->{}", code, stringBuilder.toString());
        }else{
//            log.info("httpRequest URL->" + url);
//            log.info("httpRequest CODE->{}   RESPONSE->{}", code, stringBuilder.toString());
        }
        return stringBuilder.toString();
    }

    /**
     * 获取数据域数据
     *
     * @param urlApi
     * @param tokenUrl
     * @param apiRequestParamsUtils
     * @return
     */
    public static ApiResponse getByDataArea(String urlApi, String tokenUrl, ApiRequestParamsUtils apiRequestParamsUtils) {

        if(urlApi.contains("68.60.0.116") && tokenUrl.contains("test")){
            urlApi.replace("68.60.0.116","localhost");
        }

        //解析接口返回数据
        if (urlApi.contains("localhost")|| urlApi.contains("127.0.0.1") || urlApi.contains("68.32.106.159")) {
            String jsonString = JSONObject.toJSONString(apiRequestParamsUtils);
            String responsData = null;
            try {
                responsData = doApiPost(urlApi, jsonString, "");
            } catch (IOException e) {
                e.printStackTrace();
                return ApiResponse.builder().isSuccess(false).msg("平台转发请求异常，请联系系统管理员").build();
            }
//            log.info(responsData);
            //解析接口返回数据
            ApiResponse apiResponse = ApiResponseUtils.analyseResponseData(responsData);
            return apiResponse;
        }
        String accessToken = null;
        try {
            accessToken = getGetewayToken(tokenUrl);
            if(accessToken.equals("")){
                return ApiResponse.builder().isSuccess(false).msg("获取平台令牌失败，请联系系统管理员").build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("params", apiRequestParamsUtils);
        String jsonString = JSONObject.toJSONString(map);
        String responsData = null;
        try {
            responsData = doApiPost(urlApi, jsonString, accessToken);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.builder().isSuccess(false).msg("平台转发请求异常，请联系系统管理员").build();
        }
        //解析接口返回数据
        ApiResponse apiResponse = ApiResponseUtils.analyseResponseData(responsData);
        return apiResponse;
    }

    /**
     * 获取天河接口token
     *
     * @param tokenUrl
     * @return
     */
    public static String getGetewayToken(String tokenUrl) throws IOException {
        String accessToken = "";
        //解析接口返回数据
        Map<String, String> mapBody = new HashMap<String, String>();
        mapBody.put("appid", "ZZBH");
        mapBody.put("secret", "202105281005246600485D-ED5J-4962-9B29-42009F868290");
//        mapBody.put("grant_type", "client_credentials");
//        mapBody.put("scope", "email");
        String body = JSONObject.toJSONString(mapBody);
        //accessToken = doApiPost(tokenUrl, body, "");
        if (null == RedisUtil.get("accessToken")) {
            String tokenStr = doApiPost(tokenUrl, body, "");
            if(StringUtils.isNotEmpty(tokenStr)){
                JSONObject tokenObject = JSONObject.parseObject(tokenStr);
                accessToken = tokenObject.getString("token_type") + " " + tokenObject.getString("access_token");
                RedisUtil.set("accessToken", accessToken, Integer.parseInt(tokenObject.get("expires_in").toString()));
            }
        } else {
            accessToken = RedisUtil.get("accessToken").toString();
        }
        return accessToken;
    }


    
}
