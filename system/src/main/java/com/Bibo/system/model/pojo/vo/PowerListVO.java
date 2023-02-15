package com.Bibo.system.model.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "PowerListVO对象",description = "权限返回列表对象")
public class PowerListVO  {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
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
     * 连接地址
     */
    @ApiModelProperty(example = "/test", value = "连接地址",required = true)
    private String path;

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

    @ApiModelProperty("子目录")
    private List<PowerListVO> children;


    /**
     * 状态(0正常 1停用 2删除)
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;


    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private Integer remark;

}
