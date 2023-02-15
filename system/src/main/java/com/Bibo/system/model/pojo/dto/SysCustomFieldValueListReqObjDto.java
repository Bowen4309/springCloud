package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "SysCustomFieldValueListReqObjDto",description = "自定义表单字段值查询对象")
public class SysCustomFieldValueListReqObjDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表单主键id")
    private String formId;

    @ApiModelProperty(value = "页号")
    private Integer pageNum;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

    @ApiModelProperty(value = "条件集合")
    private List<SysCustomFieldValueListReqDto> dtoList;


}
