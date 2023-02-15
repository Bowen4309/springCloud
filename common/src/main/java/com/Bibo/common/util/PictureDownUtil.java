package com.Bibo.common.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.UUID;

public class PictureDownUtil {

    public static void downPicture(String pictureUrl,String path,String name){
        try {
            File fileFold = new File(path);
            if(!fileFold.exists() && !fileFold.isDirectory()){
                System.out.println("文件夹不存在");
                fileFold.mkdir();
            }else{
                System.out.println("文件夹存在");
            }
            File file = new File(name);
            FileOutputStream out = new FileOutputStream(file);
            URL url= new URL(pictureUrl);
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = is.read(data)) != -1){
                out.write(data,0,len);
            }
            out.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pictureLook(String path, HttpServletResponse response, HttpServletRequest request){
        try {
            FileInputStream in = new FileInputStream(path);
            ServletOutputStream out = response.getOutputStream();
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = in.read(buffer)) != -1){
                out.write(buffer);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String uploadPicture(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        fileName =UUID.randomUUID().toString().replace("-","")+fileName.substring(fileName.lastIndexOf("."))+"/";
        String path = RequestParamsUtil.getPath()+ DateUtils.getDateDayToStr(new Date());
        File fileFold = new File(path);
        if(!fileFold.exists() && !fileFold.isDirectory()){
            System.out.println("文件夹不存在");
            fileFold.mkdir();
        }else{
            System.out.println("文件夹存在");
        }
        File saveFile = new File(fileFold,fileName);
        file.transferTo(saveFile);
        String path1 = saveFile.getPath();
        return DateUtils.getDateDayToStr(new Date())+"/"+fileName;
    }

    public static String getPathByUploadPicture(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        fileName =UUID.randomUUID().toString().replace("-","")+fileName.substring(fileName.lastIndexOf("."))+"/";
        String path = RequestParamsUtil.getPath()+ DateUtils.getDateDayToStr(new Date());
        File fileFold = new File(path);
        if(!fileFold.exists() && !fileFold.isDirectory()){
            System.out.println("文件夹不存在");
            fileFold.mkdir();
        }else{
            System.out.println("文件夹存在");
        }
        File saveFile = new File(fileFold,fileName);
        file.transferTo(saveFile);
        String path1 = saveFile.getPath();
        return path1;
    }

    public static int getBytLengthByUrl(String pictureUrl){
        URLConnection connection = null;
        try {
            URL url = new URL(pictureUrl);
            connection =  url.openConnection();
            //connection.setRequestMethod("post");
            connection.setConnectTimeout(20*1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(connection.getInputStream(),output);
            byte[] bytes = output.toByteArray();
            return bytes.length;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
