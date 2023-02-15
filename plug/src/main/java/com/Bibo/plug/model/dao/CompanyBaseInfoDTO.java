package com.Bibo.plug.model.dao;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "CarIndexResponseDTO",description = "标签查询车辆数据")
public class CompanyBaseInfoDTO {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("企业编号")
    private String code;

    @ApiModelProperty("企业名称")
    private String name;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("联系方式")
    private String phone;

    @ApiModelProperty("所属大队")
    private String placeDept;

    @ApiModelProperty("活跃大队")
    private String activeDept;

    @ApiModelProperty("车辆数")
    private Integer carNum;

    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("人工标签")
    private String handworkTag;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createTime;



}
