package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "类型及指标项")
public class BusinessTotalListResponDTO {

    @ApiModelProperty("指标类型ID")
    private String id;
    @ApiModelProperty("指标类型名称")
    private String name;
    @ApiModelProperty("业务指标")
    private List<BusinessTotalRsponsDTO> childBusinessTotal;
}
