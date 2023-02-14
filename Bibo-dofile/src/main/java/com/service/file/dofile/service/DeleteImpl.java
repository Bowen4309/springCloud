package com.service.file.dofile.service;

import com.service.file.dofile.repository.DeleteRespository;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.service
 * @ClassName: DeleteImpl
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 17:48
 * @Version: 1.0
 */
@Component
public class DeleteImpl implements DeleteRespository {

    @Override
    public boolean deleteFile(String url) {
        File file = new File(url);
        if(file.exists()){
            return file.delete();
        }else{
            return  true;
        }
    }
}
