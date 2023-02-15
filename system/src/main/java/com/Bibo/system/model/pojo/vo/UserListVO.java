package com.Bibo.system.model.pojo.vo;

import com.Bibo.common.pojo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "UserListVo对象",description = "用户列表对象")
public class UserListVO extends BaseVO {

    /**
     * id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 警号
     */
    @ApiModelProperty(value = "警号")
    private String policeCode;


    /**
     * 六位管理部门+六位日期+六位序列
     */
    @ApiModelProperty(value = "用户标识 六位管理部门+六位日期+六位序列")
    private String userCode;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码")
    private String password;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private String deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;


    /**
     * 角色对象
     */
    @ApiModelProperty(value = "角色对象")
    private List<String> roleList;

    @ApiModelProperty(value = "网格id")
    private String gridId;

    @ApiModelProperty(value = "网格名称")
    private String gridName;

    @ApiModelProperty("身份证号码")
    private String idCard;
}
