package com.Bibo.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FTPParamsUtil {

    //ftp专网文件交互地址
    private static String fileChangeFtpUrl;

    //ftp端口
    private static Integer ftpPort;

    //ftp账号
    private static String ftpUser;

    //ftp密码
    private static String ftpPassWord;

    //本地车牌EXCEL地址
   // private static String localExcelPath;

    //下载图片到本地保存地址
    private static String localPicturePath;

    //图片上传的车牌EXCEL
    private static String ftpExcelPath;

    //ftp下载图片地址
    private static String ftpPicturePath;


    public static String getFileChangeFtpUrl() {
        return fileChangeFtpUrl;
    }

    @Value("${fileChangeFtpUrl}")
    public void setFileChangeFtpUrl(String fileChangeFtpUrl) {
        FTPParamsUtil.fileChangeFtpUrl = fileChangeFtpUrl;
    }

    public static Integer getFtpPort() {
        return ftpPort;
    }

    @Value("${ftpPort}")
    public  void setFtpPort(Integer ftpPort) {
        FTPParamsUtil.ftpPort = ftpPort;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    @Value("${ftpUser}")
    public  void setFtpUser(String ftpUser) {
        FTPParamsUtil.ftpUser = ftpUser;
    }

    public static String getFtpPassWord() {
        return ftpPassWord;
    }
    @Value("${ftpPassWord}")
    public  void setFtpPassWord(String ftpPassWord) {
        FTPParamsUtil.ftpPassWord = ftpPassWord;
    }

//    public static String getLocalExcelPath() {
//        return localExcelPath;
//    }
//    @Value("${localExcelPath}")
//    public  void setLocalExcelPath(String localExcelPath) {
//        FTPParamsUtil.localExcelPath = localExcelPath;
//    }

    public static String getLocalPicturePath() {
        return localPicturePath;
    }
    @Value("${localPicturePath}")
    public  void setLocalPicturePath(String localPicturePath) {
        FTPParamsUtil.localPicturePath = localPicturePath;
    }

    public static String getFtpExcelPath() {
        return ftpExcelPath;
    }
    @Value("${ftpExcelPath}")
    public  void setFtpExcelPath(String ftpExcelPath) {
        FTPParamsUtil.ftpExcelPath = ftpExcelPath;
    }

    public static String getFtpPicturePath() {
        return ftpPicturePath;
    }
    @Value("${ftpPicturePath}")
    public  void setFtpPicturePath(String ftpPicturePath) {
        FTPParamsUtil.ftpPicturePath = ftpPicturePath;
    }
}
