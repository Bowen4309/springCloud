package com.Bibo.system.model.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: CGF
 * @CreateTime: 2021/10/26 19:55
 * @Description:
 */
@Data
@ApiModel(value = "PowerTreeVO对象",description = "权限树形管理返回对象")
public class PowerTreeVO {

    /**
     * 主键id
     */
    @ApiModelProperty(example = "123", value = "主键id")
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
     * 是否显示（0显示 1隐藏）
     */
    @ApiModelProperty(example = "0", value = "是否显示（0显示 1隐藏）")
    private String visible;

    /**
     * 编码
     */
    @ApiModelProperty(example = "test:test", value = "编码",required = true)
    private String code;

    /**
     * 图标
     */
    @ApiModelProperty(example = "", value = "图标")
    private String icon;

    /**
     * 路径
     */
    @ApiModelProperty(example = "/test", value = "路径",required = true)
    private String path;


    /**
     * 状态(0正常 1停用 2删除)
     */
    @ApiModelProperty(example = "1", value = "状态(0正常 1停用 2删除)")
    private String status;

    /**
     * 父菜单
     */
    @ApiModelProperty(example = "1", value = "父菜单")
    private String parentId;


    /**
     * 组件名称
     */
    @ApiModelProperty(example = "test", value = "组件名称",required = true)
    private String componentName;


    /**
     * 组件引用地址
     */
    @ApiModelProperty(example = "", value = "组件引用地址",required = true)
    private String component;

    /**
     * 父节列表
     */
    @ApiModelProperty(example = "", value = "父节列表")
    private String ancestors;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单")
    private List<PowerTreeVO> children = new ArrayList<>();
}
