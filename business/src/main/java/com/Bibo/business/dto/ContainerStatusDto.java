package com.Bibo.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "ContainerStatusDto",description = "集装箱状态信息")
public class ContainerStatusDto {

    @ApiModelProperty("提单号")
    private String blNo;

    @ApiModelProperty("状态代码")
    private String statusCd;

    @ApiModelProperty("状态描述")
    private String stateDescription;

    @ApiModelProperty("状态时间")
    private String statusTime;

    @ApiModelProperty("状态地点")
    private String statusPlace;

    @ApiModelProperty("码头")
    private String statusTerminal;

    @ApiModelProperty("是否预计时间（Y=是，N=否）")
    private String isEst;

    @ApiModelProperty("无效标识（Y=已退载或甩柜）")
    private String cancelId;

    @ApiModelProperty("船名")
    private String vslName;

    @ApiModelProperty("航次")
    private String voy;

}
