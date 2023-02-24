package com.Bibo.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "ShipmasterReqeustDto",description = "船司数据")
public class ShipmasterReqeustDto {

    @ApiModelProperty("船司代码")
    private String carrierCd;

    @ApiModelProperty("船司名称")
    private String carrier;

    @ApiModelProperty("订舱箱量")
    private String bkgVolumn;

    @ApiModelProperty("订阅号 (对应查询接口传的subNo)")
    private String referenceNo;

    @ApiModelProperty("提单号")
    private String blNo;

    @ApiModelProperty("订舱号")
    private String bkgNo;

    @ApiModelProperty("箱号")
    private String ctnrNo;

    @ApiModelProperty("客户自定义扩展信息")
    private String extraInfo;

    @ApiModelProperty("是否是箱号订阅（0=否;1=是）")
    private String isCtnr;

    @ApiModelProperty("当前节点地点")
    private String currentNodePlace;

    @ApiModelProperty("当前节点时间")
    private String currentNodeTime;

    @ApiModelProperty("当前节点")
    private String currentNode;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("起始港")
    private String pol;

    @ApiModelProperty("起始港简称")
    private String polCd;

    @ApiModelProperty("目的港")
    private String dtp;

    @ApiModelProperty("目的港简称")
    private String dtpCd;

    @ApiModelProperty("英文船名")
    private String vslNameEn;

    @ApiModelProperty("中文船名")
    private String vslNameZh;

    @ApiModelProperty("航次")
    private String voy;

    @ApiModelProperty("当前船名")
    private String currentVslName;

    @ApiModelProperty("当前航次")
    private String currentVoy;

    @ApiModelProperty("预计离港时间")
    private String etdpol;

    @ApiModelProperty("预计到港时间")
    private String etapld;

    @ApiModelProperty("首次预计离港时间")
    private String originalEtd;

    @ApiModelProperty("首次预计到港时间")
    private String originalEta;

    @ApiModelProperty("预计到达目的地时间")
    private String etaDestination;

    @ApiModelProperty("是否完成 Y-是 N-否")
    private String isCompleted;

    @ApiModelProperty("转运港")
    private String transshipmentPort;

    @ApiModelProperty("卸货港")
    private String dischargePort;

    @ApiModelProperty("预计出发(装船)时间")
    private String estimatedOnboardDate;

    @ApiModelProperty("起始地")
    private String reception;

    @ApiModelProperty("目的地")
    private String destination;

    @ApiModelProperty("数据是否有更新 true-有刷新 false无")
    private String dataRefresh;

    @ApiModelProperty("数据刷新时间")
    private String refreshTime;

    @ApiModelProperty("航段信息")
    private List<LegInfoDto> routingInfoList;

    @ApiModelProperty("集装箱信息")
    private List<ContainerInfoDto> cntrInfoList;




}
