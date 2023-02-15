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
@ApiModel(value = "TagTypeDTO对象",description = "标签分类入参")
public class TagTypeDTO {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("父级id")
    private String parentId;
    @ApiModelProperty("父级名称")
    private String parentName;
    @ApiModelProperty("分类级别")
    private String level;
    @ApiModelProperty("序号")
    private Integer index;
}
