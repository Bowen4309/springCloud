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
 * @CreateTime: 2021/10/27 9:35
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RoleListDTO对象",description = "角色列表对象")
public class RoleListDTO extends PageRequest implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    @ApiModelProperty(example = "测试人员", value = "角色名")
    private String roleName;
}
