package com.Bibo.common.pojo.vo;

import com.Bibo.common.annotation.FiledConverted;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "GridListTreeVO对象",description = "网格列表数据对象")
public class GridListTreeVO {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("网格名称")
    private String name;
    @ApiModelProperty("父网格id")
    private String parentId;
    @ApiModelProperty("父网格名称")
    private String parentName;
    @ApiModelProperty("级别")
    @FiledConverted(dictType = "grid_level",getCodeOrName = 0)
    private String level;
    @ApiModelProperty("子网格对象")
    private List<GridListTreeVO> childList;
}
