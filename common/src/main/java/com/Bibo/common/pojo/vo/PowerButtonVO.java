package com.Bibo.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "PowerButtonVO",description = "路由按钮返回对象")
public class PowerButtonVO {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private String powerId;


    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String powerName;


    @ApiModelProperty("代码")
    private String remark;


    /**
     * 父菜单ID
     */
    @ApiModelProperty("父菜单ID")
    private String parentId;

    /**
     * 组件名称
     */
    @ApiModelProperty("目录地址")
    private String componentName;


    @ApiModelProperty("子目录")
    private List<PowerButtonVO> children;
}
