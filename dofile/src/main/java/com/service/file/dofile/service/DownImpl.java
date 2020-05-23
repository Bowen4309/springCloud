package com.service.file.dofile.service;

import com.service.file.dofile.repository.DownResponsitory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.service
 * @ClassName: DownImpl
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 15:58
 * @Version: 1.0
 */
@Component
public class DownImpl implements DownResponsitory {

    @Override
    public boolean downFile(String url,String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        File file =new File(url);
        if(file.exists()){
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            /*response.setContentType("application/force-download");*/
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i =bis.read(buffer);
                while(i !=-1){
                    os.write(buffer,0,i);
                    i= bis.read(buffer);
                }
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }finally {
                if(bis!=null){
                    try {

                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(fis!=null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
