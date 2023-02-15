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
@ApiModel(value = "RoleDTO对象",description = "角色对象")
public class RoleDTO implements Serializable{


    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(example = "123", value = "主键id(编辑时必传，新增不传)")
    private String roleId;

    /**
     * 角色名
     */
    @ApiModelProperty(example = "测试人员", value = "角色名",required = true)
    private String roleName;

    /**
     * 数据范围（1：全部 2：本部门数据 3：本部门及以下数据）
     */
    @ApiModelProperty(example = "1", value = "数据范围（1：全部 2：本部门数据 3：本部门及以下数据）",required = true)
    private String dataScope;

    /**
     * 备注
     */
    @ApiModelProperty(example = "123", value = "备注")
    private String remark;


}
