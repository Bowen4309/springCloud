package com.Bibo.system.model.pojo.dto;


import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysDictDataPageReqDto对象",description = "数据字典列表请求封装类")
public class SysDictDataPageReqDto extends PageRequest {

    @ApiModelProperty("字典类型")
    private String type;

}
