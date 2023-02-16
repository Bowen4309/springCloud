package com.Bibo.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "LegInfoDto",description = "航段信息")
public class LegInfoDto {

    @ApiModelProperty("箱号")
    private String ctnrNo;

    @ApiModelProperty("起运港")
    private String  loading;

    @ApiModelProperty("目的港")
    private String discharging;

    @ApiModelProperty("预计出发时间")
    private String etd;

    @ApiModelProperty("实际出发时间")
    private String atd;

    @ApiModelProperty("预计抵达时间")
    private String eta;

    @ApiModelProperty("实际抵达时间")
    private String ata;

    @ApiModelProperty("船名")
    private String vslName;

    @ApiModelProperty("航次")
    private String voy;

    @ApiModelProperty("抵达时间")
    private String arrivalTime;

    @ApiModelProperty("出发时间")
    private String  departureTime;
}
