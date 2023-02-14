package com.service.file.dofile.repository;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.repository
 * @ClassName: DownResponsitory
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 15:57
 * @Version: 1.0
 */
public interface DownResponsitory {

    boolean downFile(String url,String fileName, HttpServletResponse response) throws UnsupportedEncodingException;
}
