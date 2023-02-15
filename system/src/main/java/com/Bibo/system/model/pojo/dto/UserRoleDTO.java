package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "UserRoleDTO对象",description = "角色赋予用户对象")
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    @ApiModelProperty(example = "1", value = "角色主键id")
    private String roleId;

    /**
     * 用户ID列表
     */
    @ApiModelProperty(value = "用户ID列表")
    private String[] userIds;
}
