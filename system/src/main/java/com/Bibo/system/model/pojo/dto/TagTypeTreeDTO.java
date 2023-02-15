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
@ApiModel(value = "TagTypeTreeDTO对象",description = "标签分类树状数据")
public class TagTypeTreeDTO extends TagTypeDTO{

   private List<TagTypeTreeDTO> childTagType;
}
