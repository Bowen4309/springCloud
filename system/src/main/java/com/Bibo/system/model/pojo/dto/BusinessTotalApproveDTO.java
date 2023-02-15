package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.annotation.FiledConverted;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BusinessTotalApproveDTO对象",description = "业务指标审核信息对象")
public class BusinessTotalApproveDTO {

    @ApiModelProperty("id")
    private String id;
    
    @ApiModelProperty("指标名称")
    private String name;

    @ApiModelProperty("审核状态(1审批中，2已通过，3被驳回,4:未提交)")
    private String status;

    @ApiModelProperty("指标父级名称")
    private String parentName;

    @ApiModelProperty("指标统计层级（0:个人;1:中队;2:大队;3:支队）")
    @FiledConverted(dictType = "sys_target_level",getCodeOrName = 0)
    private String level;

    @ApiModelProperty("展示类型(饼图，柱状图，列表等前端自定义规则)")
    private String showType;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("备注注解")
    private String remark;
}
