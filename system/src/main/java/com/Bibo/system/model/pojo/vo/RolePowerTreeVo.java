package com.Bibo.system.model.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TreePowerVo",description = "角色拥有权限管理对象")
public class RolePowerTreeVo {

    /**
     * 拥有的目录
     */
    @ApiModelProperty(value = "拥有的目录")
    private List<String> menus;

    /**
     * 选择的权限树结构
     */
    @ApiModelProperty(value = "选择的权限树结构")
    private List<PowerTreeVO> checkedKeys;
}
