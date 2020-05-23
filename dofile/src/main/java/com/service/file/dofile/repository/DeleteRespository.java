package com.service.file.dofile.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.repository
 * @ClassName: DeleteRespository
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 17:43
 * @Version: 1.0
 */
@Mapper
@Component
public interface DeleteRespository {


    boolean deleteFile(String url);
}
