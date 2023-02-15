package com.Bibo.common.pojo;

import com.Bibo.common.pojo.vo.DeptListVO;
import com.Bibo.common.pojo.vo.RoleListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "LoginUser对象",description = "用户对象")
public class LoginUser {


    /**
     * 用户唯一标识
     */
    @ApiModelProperty(value = "用户唯一标识")
    private String token;

    /**
     * id
     */
    @ApiModelProperty(example = "id", value = "用户id")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty(example = "lisi", value = "用户名", required = true)
    private String userName;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "真实姓名")
    private String nickName;


    /**
     * 用户密码
     */
    @ApiModelProperty(example = "123456", value = "用户密码")
    private String password;

    /**
     * 警号
     */
    @ApiModelProperty(value = "警号")
    private String policeCode;

    /**
     * 网格ID
     */
    @ApiModelProperty(value = "网格ID")
    private String gridId;

    /**
     * 性别
     */
    @ApiModelProperty(example = "1", value = "性别")
    private String sex;


    @ApiModelProperty(value = "网格名称")
    private String gridName;

    /**
     * 状态(0正常 1停用 2删除)
     */
    @ApiModelProperty(example = "0", value = "状态(0正常 1停用 2删除)")
    private String status;

    /**
     * 角色对象
     */
    @ApiModelProperty(value = "角色对象")
    private List<RoleListVO> roleList;


    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private DeptListVO dept;


    /**
     * 权限列表
     */
    @ApiModelProperty(value = "角色对象")
    private List<String> permissions;

    /**
     * 登录时间
     */
    @ApiModelProperty(name = "登录时间")
    private Long loginTime;

    /**
     * 过期时间
     */
    @ApiModelProperty(name = "过期时间")
    private Long expireTime;

    @ApiModelProperty("身份证号码")
    private String idCard;

    @ApiModelProperty("是否强密码")
    private boolean strongPassWord;

}
