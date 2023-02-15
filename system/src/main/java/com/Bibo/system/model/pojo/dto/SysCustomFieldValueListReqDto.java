package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysCustomFieldValueListReqDto",description = "自定义表单字段值查询对象")
public class SysCustomFieldValueListReqDto implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty(value = "字段主键id")
    private String fieldId;

    @ApiModelProperty(value = "字段值")
    private String varcharValue;

    @ApiModelProperty(value = "比较类型:1=相等；2=大于（开始时间）；3=小于（结束时间）")
    private Integer type;



}
