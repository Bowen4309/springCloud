package com.Bibo.common.pojo.vo;


import com.Bibo.common.pojo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "RoleListVO对象",description = "角色返回列表对象")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleListVO extends BaseVO {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private String roleId;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 角色权限
     */
    @ApiModelProperty(value = "角色权限")
    private String roleKey;

    /**
     * 数据范围（1：全部 2：本部门数据 3：本部门及以下数据）
     */
    @ApiModelProperty(value = "数据范围（1：全部 2：本部门数据 3：本部门及以下数据）")
    private String dataScope;

}
