package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "UserRolesDTO对象",description = "用户绑定角色对象")
public class UserRolesDTO {

    /**
     * 用户主键id
     */
    @ApiModelProperty(example = "123", value = "用户主键id")
    private List<String> userId;

    /**
     * 角色组
     */
    @ApiModelProperty(value = "角色组")
    private List<String> roleList;
}
