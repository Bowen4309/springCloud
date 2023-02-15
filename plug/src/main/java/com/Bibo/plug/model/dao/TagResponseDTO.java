package com.Bibo.plug.model.dao;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "TagResponseDTO",description = "标签返回对象")
public class TagResponseDTO {

    private String objectName;

    private Map<String, List<String>> tag;
}
