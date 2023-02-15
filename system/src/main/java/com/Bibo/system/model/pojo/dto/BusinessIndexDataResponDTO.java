package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "BusinessIndexDataResponDTO对象",description = "业务指标查询返回数据对象")
public class BusinessIndexDataResponDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("业务指标类型")
    private String parentName;
    @ApiModelProperty("指标名称")
    private String name;
    @ApiModelProperty("指标图标")
    private String log;

    @ApiModelProperty("指标例图")
    private String showPicture;

    @ApiModelProperty("支队下的大队")
    private List<Object> level_1;

    @ApiModelProperty("大队下的中队")
    private List<Object> level_2;

    @ApiModelProperty("中队下的民警")
    private List<Object> level_3;

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("展示类型(饼图，柱状图，列表等前段自定义规则)")
    private String showType;

    @ApiModelProperty("数据类型(个人数据、部门统计数据、分析数据)")
    private String dataType;

    @ApiModelProperty("指标统计时间类型(小时、日、月、年)")
    private String timeType;

    @ApiModelProperty("时间值")
    private String timeNum;


    @ApiModelProperty("指标类型(统计指标、分析指标等)")
    private String type;

}
