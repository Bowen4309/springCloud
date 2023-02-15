package com.Bibo.gateway.utils;


import com.Bibo.gateway.constant.UrlEnum;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.io.IOException;


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
     * @param url
     * @return
     */
    public static String doGet(String url) {
        //1.生成HttpClient对象并设置参数
        HttpClient httpClient = new HttpClient();
        Protocol https = new Protocol("https",new HTTPSSecurePotocolocketFactory(),443);
        Protocol.registerProtocol("https",https);
        //设置Http连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        //2.生成GetMethod对象并设置参数
        GetMethod getMethod = new GetMethod(url);
        //设置get请求超时为5秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        //设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
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
            for(Header h : headers) {
                System.out.println(h.getName() + "---------------" + h.getValue());
            }
            //读取HTTP响应内容，这里简单打印网页内容
            //读取为字节数组
            byte[] responseBody = getMethod.getResponseBody();
            response = new String(responseBody, "UTF-8");
            System.out.println("-----------response:" + response);
            //读取为InputStream，在网页内容数据量大时候推荐使用
            //InputStream response = getMethod.getResponseBodyAsStream();
            Protocol.unregisterProtocol("https");
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
     * @param url
     * @param
     * @return
     */
    public static String  doApiPost(String url, String body) throws IOException {

        HttpClient httpClient = new HttpClient();
        Protocol https = new Protocol("https",new HTTPSSecurePotocolocketFactory(),443);
        Protocol.registerProtocol("https",https);
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
//        if(StringUtils.isNotEmpty(token)){
//            postMethod.addRequestHeader("token",token);
//        }
        String res = "";
        if(StringUtils.isNotEmpty(body)){
            StringRequestEntity entity = new StringRequestEntity(body,"application/json","UTF-8");
            postMethod.setRequestEntity(entity);
        }
        int code = httpClient.executeMethod(postMethod);
        Protocol.unregisterProtocol("https");
        if (code == 200){
            res = postMethod.getResponseBodyAsString();
            System.out.println(res);
        }
        return res;
    }





    public static void main(String[] args) {
      //  System.out.println(doGet("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071", "GBK"));
        System.out.println(doGet(UrlEnum.CHECK_USER_URL+"?userToken=123456"));
        System.out.println("-----------分割线------------");
        System.out.println("-----------分割线------------");
        System.out.println("-----------分割线------------");

        //JSONObject jsonObject = new JSONObject();
       // jsonObject.put("commentId", "13026194071");
       // System.out.println(doPost("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071", jsonObject.toJSONString()));
    }
}
