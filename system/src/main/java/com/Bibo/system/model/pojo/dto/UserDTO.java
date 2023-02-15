package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: CGF
 * @CreateTime: 2021/10/27 19:01
 * @Description:
 */

@Data
@ApiModel(value = "UserDTO对象",description = "用户对象")
public class UserDTO{

    /**
     * id
     */
    @ApiModelProperty(example = "id", value = "用户id(编辑时必传，新增不传)")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty(example = "lisi", value = "用户名", required = true)
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty(example = "123456", value = "用户新密码")
    private String password;

    /**
     * 警号
     */
    @ApiModelProperty(value = "警号", required = true)
    private String policeCode;

    /**
     * 部门id
     */
    @ApiModelProperty(example = "1", value = "部门id", required = true)
    private String deptId;

    /**
     * 性别
     */
    @ApiModelProperty(example = "1", value = "性别")
    private String sex;

    /**
     * 角色ID
     */
    @ApiModelProperty(name = "网格id")
    private String gridId;

    /**
     * 角色ID
     */
    @ApiModelProperty(name = "网格名称")
    private String gridName;

    @ApiModelProperty(name ="身份证号码")
    private String idCard;

    @ApiModelProperty(name = "旧密码")
    private String oldPassWord;
}