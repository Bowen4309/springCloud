package com.Bibo.system.model.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "路由显示信息",description = "路由显示信息返回对象")
public class MetaVO {

    @ApiModelProperty(value = "name", example = "设置路由展示的名称")
    private String title;

    @ApiModelProperty(value = "name", example = "路由图标")
    private String icon;

}
