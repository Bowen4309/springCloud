package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysCustomFieldValueDto",description = "自定义表单字段修改对象")
public class SysCustomFieldValueDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "字段主键id")
    private String fieldId;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "表单主键id")
    private String formId;

    @ApiModelProperty(value = "自定义字段值：文本")
    private String varcharValue;

    @ApiModelProperty(value = "自定义字段值：数字")
    private Integer intValue;

    @ApiModelProperty(value = "自定义字段值：浮点数")
    private Double floatValue;

    @ApiModelProperty(value = "自定义字段值：日期")
    private Date datetimeValue;

    @ApiModelProperty(value = "是否删除：1是0否")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "默认行号")
    private Integer defaultRowNo;

    @ApiModelProperty(value = "展示排序字段")
    private Integer showSort;



}
