package com.Bibo.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RequestParamsUtil {

    //天河接口地址
    private static String urlApi;

    //天河接口获取token地址
    private static String urlToken;

    //图片保存地址
    private static String path;

    //专网文件交互地址
    private static String fileChangeFtpUrl;

    //文件下载地址
    private static String filePath;


    @Value("${urlApi}")
    public void setUrlApi(String urlApi) {
        RequestParamsUtil.urlApi = urlApi;
    }

    @Value("${urlToken}")
    public void setUrlToken(String urlToken) {
        RequestParamsUtil.urlToken = urlToken;
    }

    public static String getUrlApi(){
        return urlApi;
    }

    public static String getUrlToken(){
        return urlToken;
    }

    @Value("${path}")
    public void setPath(String path) {
        RequestParamsUtil.path = path;
    }

    public static String getPath(){
        return path;
    }

    public static String getFileChangeFtpUrl() {
        return fileChangeFtpUrl;
    }

    @Value("${fileChangeFtpUrl}")
    public void setFileChangeFtpUrl(String fileChangeFtpUrl) {
        RequestParamsUtil.fileChangeFtpUrl = fileChangeFtpUrl;
    }

    public static String getFilePath() {
        return filePath;
    }
    @Value("${filePath}")
    public  void setFilePath(String filePath) {
        RequestParamsUtil.filePath = filePath;
    }
}
