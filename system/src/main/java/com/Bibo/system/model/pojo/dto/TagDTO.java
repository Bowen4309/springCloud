package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "TagDTO对象",description = "标签对象")
public class TagDTO {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("标签名称")
    private String tagName;
    @ApiModelProperty("标签分类ID(多级分类;隔开)")
    private String tagTypeId;
    @ApiModelProperty("标签分类名称(多级分类;隔开)")
    private String tagTypeName;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("序号")
    private Integer index;
    @ApiModelProperty("描述")
    private String describe;
    @ApiModelProperty("模型名称")
    private String modelName;
    @ApiModelProperty("是否模型计算")
    private String isCalculation;
    @ApiModelProperty("标签对象(即1级分类名称人、车、道路、资源等)")
    private String objectName;
    @ApiModelProperty("标签对象ID(即1级分类ID)")
    private String objectId;
    @ApiModelProperty("详情信息")
    private String detail;
}
