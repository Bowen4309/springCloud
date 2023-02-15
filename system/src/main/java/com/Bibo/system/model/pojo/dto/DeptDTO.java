package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeptDTO对象",description = "部门扩展对象")
public class DeptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(example = "123", value = "主键id(编辑时必传，新增不传)")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(example = "交警支队", value = "部门名称",required = true)
    private String deptName;

    /**
     * 上级部门
     */
    @ApiModelProperty(example = "100", value = "上级部门",required = true)
    private String parentId;

//    /**
//     * 状态(0正常 1停用 2删除)
//     */
//    @ApiModelProperty(example = "1", value = "状态(0正常 1停用 2删除)")
//    private String status;

    /**
     * 行政区域
     */
    @ApiModelProperty(example = "1", value = "行政区域",required = true)
    private String administrative;

    /**
     * 部门地址
     */
    @ApiModelProperty(example = "广州XXXX", value = "部门地址")
    private String deptAddress;

}
