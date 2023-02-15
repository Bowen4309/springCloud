package com.Bibo.plug.model.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FacilityInfoDTO",description = "交通设施基础信息")
public class FacilityInfoDTO {

    @ApiModelProperty("资源id")
    private String resourceId;

    @ApiModelProperty("设施编号")
    private String facilityId;

    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("设施名称")
    private String name;

    @ApiModelProperty("纬度")
    private String latitude;

    @ApiModelProperty("经度")
    private String longitude;

    @ApiModelProperty("人工标签id")
    private String tagId;

    @ApiModelProperty("人工标签")
    private String tagName;

    @ApiModelProperty("设施地址")
    private String address;

    @ApiModelProperty("所属网格id")
    private String gridId;

    @ApiModelProperty("所属网格名称")
    private String gridName;

    @ApiModelProperty("所属部门id")
    private String deptId;

    @ApiModelProperty("所属部门名称")
    private String deptName;

}
