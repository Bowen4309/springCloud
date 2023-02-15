package com.Bibo.system.model.pojo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "RouterVO对象",description = "路由信息返回对象")
public class RouterVO {

    @ApiModelProperty(value = "name", example = "路由名称")
    private String name;

    @ApiModelProperty(value = "path", example = "路由地址")
    private String path;

    @ApiModelProperty(value = "component", example = "组件地址")
    private String component;

    @ApiModelProperty(value = "hidden", example = "是否隐藏路由，true为隐藏")
    private Boolean hidden;

    private MetaVO metaVO;

    @ApiModelProperty(value = "component", example = "子路由")
    private List<RouterVO> children;
}
