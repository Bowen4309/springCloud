package com.service.file.dofile.service;

import com.service.file.dofile.repository.PreviewRespository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.service
 * @ClassName: PreviewImpl
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/21 0021 10:40
 * @Version: 1.0
 */
@Component
public class PreviewImpl implements PreviewRespository {

    @Override
    public boolean previePdfFile(String url, String fileName, HttpServletResponse response) {
        File file = new File(url);
        if (file.exists()) {
            byte[] data = null;
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                System.out.println("pdf文件处理异常：" + e);
                return false;
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
