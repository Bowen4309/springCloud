package com.Bibo.system.model.pojo.vo;

import com.Bibo.common.pojo.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登陆返回对象",description = "路由显示信息返回对象")
public class LoginUserVO {

    @ApiModelProperty(value = "token", example = "验证token")
    private String token;

    @ApiModelProperty(value = "user", example = "用户信息")
    private UserVO user;

    @ApiModelProperty(value = "perms", example = "权限数组")
    private List<String> perms;

    @ApiModelProperty(value = "roles", example = "角色数组")
    private List<String> roles;
}
