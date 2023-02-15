package com.Bibo.common.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "ResourceInfoDTO对象",description = "资源详情请求对象")
public class ResourceInfoDTO {

    /**
     * 资源id
     */
    @ApiModelProperty("资源id")
    private String resourceId;

    /**
     * 对象id
     */
    @ApiModelProperty("对象id")
    private String objectId;
}
