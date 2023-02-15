package com.Bibo.common.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageRequest {

    /**
     * 页码
     */
    @ApiModelProperty(example = "1", value = "页码", required = true)
    private Long pageNum;

    /**
     * 页数
     */
    @ApiModelProperty(example = "10", value = "页数", required = true)
    private Long pageSize;

    @ApiModelProperty("开始日期")
    private String startDate;


    @ApiModelProperty("结束日期")
    private String endDate;


    @ApiModelProperty("排序字段")
    private String code;

    @ApiModelProperty("排序规则(desc倒叙/asc顺序)")
    private String rule;

}
