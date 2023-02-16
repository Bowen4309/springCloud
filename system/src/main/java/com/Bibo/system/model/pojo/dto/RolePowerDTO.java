package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "RolePowerDTO对象",description = "授权角色对象")
public class RolePowerDTO {

    /**
     * 主键id
     */
    @ApiModelProperty(example = "123", value = "主键id(编辑时必传，新增不传)")
    private String roleId;

    /**
     * 菜单组
     */
    @ApiModelProperty(value = "菜单组")
    private List<String> powerIds;
}
