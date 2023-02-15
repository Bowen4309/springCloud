package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PowerButtonEditDTO对象",description = "编辑对象")
public class PowerButtonEditDTO {


    @ApiModelProperty("按钮ID")
    private String powerId;

    @ApiModelProperty("按钮名称")
    private String powerName;

    @ApiModelProperty("序号")
    private Integer remark;

    @ApiModelProperty("路由名称")
    private String componentName;

}
