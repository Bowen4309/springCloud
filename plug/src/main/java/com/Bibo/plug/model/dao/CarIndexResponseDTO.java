package com.Bibo.plug.model.dao;

import com.Bibo.common.annotation.FiledConverted;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "CarIndexResponseDTO",description = "标签查询车辆数据")
public class CarIndexResponseDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("车牌号码")
    private String carNumber;

    @ApiModelProperty("号牌种类")
  //  @FiledConverted(dictType = "DM00306",getCodeOrName = 0)
    private String carNumberType;

    @ApiModelProperty("号牌种类Code")
    private String carNumberTypeCode;

    @ApiModelProperty("标签")
    private String tag;

    @ApiModelProperty("号牌颜色")
    private String carNumberColor;
}
