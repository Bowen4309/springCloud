package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "SysCustomFormFieldStatusUpdateDto",description = "自定义表单状态修改对象")
public class SysCustomFormFieldStatusUpdateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "是否启用：1是0否")
    private Integer status;

    @ApiModelProperty(value = "是否删除：1是0否")
    private Integer isDelete;


}
