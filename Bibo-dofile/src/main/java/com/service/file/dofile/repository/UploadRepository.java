package com.service.file.dofile.repository;

import com.service.file.dofile.entity.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.repository
 * @ClassName: UploadRepository
 * @Author: yunabo
 * @Description: ${description}
 * @Date: 2020/4/10 0010 10:01
 * @Version: 1.0
 */
public interface UploadRepository {

    FileInfo uploadFileSingle(MultipartFile file,String proPath,String fileId);


}
