package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "SysCustomFieldValueInsertDto",description = "自定义表单字段修改对象")
public class SysCustomFieldValueInsertDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表单主键id")
    private String formId;


    @ApiModelProperty(value = "字段集合")
    private List<SysCustomFieldValueDto> dtoList;


}
