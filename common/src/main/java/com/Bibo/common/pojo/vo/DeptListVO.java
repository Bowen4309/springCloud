package com.Bibo.common.pojo.vo;

import com.Bibo.common.pojo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "DeptListVO对象",description = "部门查询返回列表对象")
public class DeptListVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 上级部门id
     */
    @ApiModelProperty(value = "上级部门id")
    private String parentId;

    /**
     * 上级部门
     */
    @ApiModelProperty(value = "上级部门")
    private String parentName;

    /**
     * 行政区域
     */
    @ApiModelProperty(value = "行政区域")
    private String administrativeValue;

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


    @ApiModelProperty("部门级别")
    private String deptLevel;
}
