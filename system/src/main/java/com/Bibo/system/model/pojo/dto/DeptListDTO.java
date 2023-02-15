package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: CGF
 * @CreateTime: 2021/10/26 19:23
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DeptListDTO对象",description = "部门扩展列表对象")
public class DeptListDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    @ApiModelProperty(example = "交警支队", value = "部门名称")
    private String deptName;

    /**
     * 上级部门
     */
    @ApiModelProperty(example = "100", value = "上级部门")
    private String parentId;

    /**
     * 上级部门
     */
    @ApiModelProperty(example = "440101", value = "ID(部门编号)")
    private String deptId;
}
