package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusinessTotalListReqDTO对象",description = "业务指标列表查询请求参数")
public class BusinessTotalListReqDTO extends PageRequest {

    @ApiModelProperty("指标父级名称")
    private String name;

    @ApiModelProperty("指标使用状态(0:停用;1:正常)")
    private String status;

}
