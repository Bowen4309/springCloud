package com.Bibo.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ContainerInfoDto",description = "集装箱信息")
public class ContainerInfoDto {

    @ApiModelProperty("箱号")
    private String ctnrNo;

    @ApiModelProperty("提单号")
    private String blNo;

    @ApiModelProperty("箱型")
    private String size;

    @ApiModelProperty("毛重")
    private String weight;

    @ApiModelProperty("vgm")
    private String vgm;

    @ApiModelProperty("件数")
    private String pieces;

    @ApiModelProperty("铅封号")
    private String sealNo;

    @ApiModelProperty("当前节点")
    private String currentNode;

    @ApiModelProperty("当前节点时间")
    private String currentNodeTime;

    @ApiModelProperty("当前节点地点")
    private String statusPlace;

    @ApiModelProperty("是否为自备箱")
    private String soc;

    @ApiModelProperty("状态描述")
    private String statusDesc;

    @ApiModelProperty("最后免费时间 (仅支持部分船司)")
    private String lastFreeDate;

    @ApiModelProperty("是否可提重箱 (仅支持部分船司)")
    private String pickupAvailable;
}
