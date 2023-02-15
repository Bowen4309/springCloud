package com.Bibo.common.util;




import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.Date;
import java.util.List;

public class UploadFileToFtpUtil{

    public static void uploadFileToFtp(InputStream is,String fileName){
        FTPClient ftpClient = new FTPClient();
        try {
            if(FTPParamsUtil.getFtpPort().toString().equals("22")){
                ftpClient.connect(FTPParamsUtil.getFileChangeFtpUrl(),FTPParamsUtil.getFtpPort());
            }else{
                ftpClient.connect(FTPParamsUtil.getFileChangeFtpUrl());
            }

            ftpClient.enterLocalPassiveMode();
            String path = FTPParamsUtil.getFtpExcelPath();
            System.out.println("ftpurl:"+FTPParamsUtil.getFileChangeFtpUrl()+
                    "----------ftpuser:"+FTPParamsUtil.getFtpUser()+
                    "-------ftppassword:"+FTPParamsUtil.getFtpPassWord()+
                    "-------------path:"+path);
            ftpClient.login(FTPParamsUtil.getFtpUser(),FTPParamsUtil.getFtpPassWord());
            int nextSeperator = path.indexOf("/",1);
            String currentPath = null;

            //切换目录，没有就新增
            if(nextSeperator <0){
                nextSeperator= path.length();
                currentPath = path.substring(1,nextSeperator);
                if(!ftpClient.changeWorkingDirectory(currentPath)){
                    ftpClient.makeDirectory(currentPath);
                }
            }else{
                currentPath = path.substring(1,nextSeperator);
            }
            System.out.println("current_path:"+currentPath+"fileName:"+fileName);
            ftpClient.changeWorkingDirectory(currentPath);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //传文件
            if(!ftpClient.storeFile(fileName,is)){
                System.out.println("上传失败");
                is.close();
            }else{
                System.out.println("上传成功");
            }
            is.close();
            //切回根目录
            ftpClient.changeWorkingDirectory("/");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ftpClient != null && ftpClient.isConnected()){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("ftp连接失败："+e.getMessage());
                }
            }
        }
    }

    public static String downPicture(List<String> fileNames){
        StringBuilder sb = new StringBuilder();
        String date = DateUtils.getDateDayToStr(new Date());
        for(String fileName:fileNames){
            FTPClient ftpClient = getFtpInfo();
            try {
                String path = FTPParamsUtil.getFtpPicturePath();
                int replyCode = ftpClient.getReplyCode();
                String loacalPath = FTPParamsUtil.getLocalPicturePath() +date;
                File localFilePath = new File(loacalPath);
                if(!localFilePath.exists()){
                    localFilePath.mkdir();
                }
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.setControlEncoding("GBK");
                if(ftpClient.changeWorkingDirectory(path)){
                    InputStream is = ftpClient.retrieveFileStream(new String(fileName.getBytes("GBK"), "ISO-8859-1"));
                    if(is != null) {
                        //FTPFile[] files =ftpClient.listFiles();
                        File localFile = new File(loacalPath +"/"+ fileName);
                        OutputStream out = new FileOutputStream(localFile);
                      //  System.out.println(new String(fileName.getBytes("GBK"), "ISO-8859-1"));
                        int index;
                        byte[] bytes = new byte[1024];
                        while ((index = is.read(bytes)) != -1) {
                            out.write(bytes, 0, index);
                            out.flush();
                        }
                        System.out.println(loacalPath);
                        sb.append(loacalPath).append("/").append(fileName).append(";");
                        is.close();
                        out.close();
                    }
                }
                System.out.println(loacalPath +"/"+ fileName);
                ftpClient.deleteFile(loacalPath +"/"+ fileName);
                ftpClient.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (ftpClient.isConnected()) {
                    try {
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.out.println("ftp连接失败：" + e.getMessage());
                    }
                }
            }
        }
        return sb.toString();
    }

    public static FTPClient  getFtpInfo() {
        FTPClient ftpClient = new FTPClient();
        try {
            if (FTPParamsUtil.getFtpPort().toString().equals("22")) {
                ftpClient.connect(FTPParamsUtil.getFileChangeFtpUrl(), FTPParamsUtil.getFtpPort());
            } else {
                ftpClient.connect(FTPParamsUtil.getFileChangeFtpUrl());
            }
            ftpClient.login(FTPParamsUtil.getFtpUser(), FTPParamsUtil.getFtpPassWord());
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
           // e.printStackTrace();
            System.out.println("连接失败："+e.getMessage());
        }

        return ftpClient;
    }

}
