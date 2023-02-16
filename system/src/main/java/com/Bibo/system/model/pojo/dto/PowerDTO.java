package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "PowerDTO对象",description = "权限管理对象")
public class PowerDTO {

    /**
     * 主键id
     */
    @ApiModelProperty(example = "123", value = "主键id(编辑时必传，新增不传)")
    private String powerId;

    /**
     * 类型(1菜单 2目录 3按钮)
     */
    @ApiModelProperty(example = "1", value = "类型(1菜单 2目录 3按钮)",required = true)
    private String powerType;

    /**
     * 名称
     */
    @ApiModelProperty(example = "测试", value = "名称",required = true)
    private String powerName;

    /**
     * 图标
     */
    @ApiModelProperty(example = "", value = "图标")
    private String icon;

    /**
     * 路径
     */
    @ApiModelProperty(example = "/test/test", value = "路径")
    private String path;

    /**
     * 组件名称
     */
    @ApiModelProperty(example = "test", value = "组件名称")
    private String componentName;


    /**
     * 组件引用地址
     */
    @ApiModelProperty(example = "", value = "组件引用地址")
    private String component;
    /**
     * 父菜单
     */
    @ApiModelProperty(example = "1", value = "父菜单")
    private String parentId;

    @ApiModelProperty(example = "0", value = "状态(0正常 1停用 2删除)")
    private String status;

    /**
     * 系统类型(1后台 2网格 3app)
     */
    @ApiModelProperty(example = "0", value = "系统类型(1后台 2网格 3app)",required = true)
    private String systemType;

}
