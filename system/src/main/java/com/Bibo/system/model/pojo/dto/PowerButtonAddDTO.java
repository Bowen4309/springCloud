package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PowerButtonAddDTO对象",description = "按钮新增对象")
public class PowerButtonAddDTO {


    @ApiModelProperty("菜单ID")
    private String parentId;

    @ApiModelProperty("名称")
    private String powerName;

    @ApiModelProperty("类型(1菜单 2目录 3按钮)")
    private String powerType;

    @ApiModelProperty("序号")
    private Integer remark;

    @ApiModelProperty("路由名称")
    private String componentName;

    /**
     * 系统类型 1后台 2网格 3app
     */
    @ApiModelProperty(example = "0", value = "系统类型 1后台 2网格 3app")
    private String systemType;

}
