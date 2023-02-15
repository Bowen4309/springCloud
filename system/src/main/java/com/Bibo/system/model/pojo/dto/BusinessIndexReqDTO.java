package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusinessIndexReqDTO对象",description = "业务指标查询对象")
public class BusinessIndexReqDTO {

    @ApiModelProperty("指标类型")
    private String parentName;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("部门(按网格部门)")
    private String depart;


}
