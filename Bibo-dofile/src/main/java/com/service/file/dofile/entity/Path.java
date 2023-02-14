package com.service.file.dofile.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: 三线一单
 * @Package: com.service.file.dofile.entity
 * @ClassName: Path
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/10 0010 11:30
 * @Version: 1.0
 */
@PropertySource(value = {"classpath:application.properties"})
@Getter
@Setter
@Component
public class Path {

    @Value("${upload.path}")
    private String path;

    @Value("${service.previewUrl}")
    private String previewServiceUrl;


    @Value("${service.localUrl}")
    private String localUrl;


}
