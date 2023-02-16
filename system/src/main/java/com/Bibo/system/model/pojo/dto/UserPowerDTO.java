package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "UserPowerDTO对象",description = "授权用户对象")
public class UserPowerDTO {

    /**
     * 用户主键id
     */
    @ApiModelProperty(example = "123", value = "用户主键id")
    private String userId;

    /**
     * 菜单组
     */
    @ApiModelProperty(value = "菜单组")
    private List<String> powerIds;
}
