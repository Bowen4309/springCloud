package com.Bibo.system.model.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "SysCustomFormFieldInsertObjDto",description = "自定义表单插入对象")
public class SysCustomFormFieldInsertObjDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级id")
    private String parentId;


    @ApiModelProperty(value = "层级")
    private Integer level;


    @ApiModelProperty(value = "字段集合")
    private List<SysCustomFormFieldInsertDto> list;



}
