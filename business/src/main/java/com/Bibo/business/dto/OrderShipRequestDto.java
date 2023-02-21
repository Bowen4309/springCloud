package com.Bibo.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "OrderShipRequestDTO",description = "船司订阅数据")
public class OrderShipRequestDto {

    @ApiModelProperty("订阅类型1: 提单号；2: 箱号; ")
    private Integer subType;

    @ApiModelProperty("船司代码 如:COSCO  ")
    private String carrierCd;

    @ApiModelProperty("订阅单号，需要和订阅类型一一对应 ")
    private String subNo;

    @ApiModelProperty("箱号 ")
    private List<String> extraInfo;

}
