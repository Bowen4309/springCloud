package com.Bibo.system.model.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: CGF
 * @CreateTime: 2021/10/27 10:43
 * @Description:
 */
@Data
@ApiModel(value = "DeptTreeVO对象",description = "部门树形返回对象")
public class DeptTreeVO {

    /**
     * 主键id
     */
    @ApiModelProperty(example = "123", value = "主键id")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(example = "交警支队", value = "部门名称",required = true)
    private String deptName;

    /**
     * 是否开启(0是关闭1是开启)
     */
    @ApiModelProperty(example = "0", value = "是否开启(0是关闭1是开启)")
    private String open;

    /**
     * 序号
     */
    @ApiModelProperty(example = "123", value = "序号")
    private Integer orderNum;

    /**
     * 上级部门
     */
    @ApiModelProperty(example = "100", value = "上级部门",required = true)
    private String parentId;

    /**
     * 祖级列表
     */
    @ApiModelProperty(example = "0,100", value = "祖级列表")
    private String ancestors;

    /**
     * 状态(0正常 1停用 2删除)
     */
    @ApiModelProperty(example = "1", value = "状态(0正常 1停用 2删除)")
    private String status;

    /**
     * 行政区域
     */
    @ApiModelProperty(value = "行政区域")
    private String administrative;

    /**
     * 部门地址
     */
    @ApiModelProperty(value = "部门地址")
    private String deptAddress;

    /**
     * 父部门名称
     */
    @ApiModelProperty(value = "父部门名称")
    private String parentName;


    /**
     * 部门级别
     */
    @ApiModelProperty(value = "部门级别")
    private String deptLevel;


    /**
     * 子部门列表
     */
    @ApiModelProperty(value = "子部门列表")
    private List<DeptTreeVO> children = new ArrayList<>();
}
