package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "LoginDTO对象",description = "登陆参数")
public class LoginDTO {

    @ApiModelProperty(value = "用户名", example = "admin")
    private String userName;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "登陆系统类型", example = "1")
    private String type;
}
