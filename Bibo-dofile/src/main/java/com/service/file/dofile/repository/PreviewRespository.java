package com.service.file.dofile.repository;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.repository
 * @ClassName: PreviewRespository
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/21 0021 10:34
 * @Version: 1.0
 */
@Component
public interface PreviewRespository {

    boolean previePdfFile(String url,String fileName,HttpServletResponse response);

}
