package com.Bibo.system.model.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.Bibo.system.model.pojo.vo.DeptTreeVO;
import com.Bibo.system.model.pojo.vo.PowerTreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@ApiModel(value = "TreeSelect",description = "树形结构对象管理对象")
public class TreeSelect {

    /**
     * 节点id
     */
    @ApiModelProperty(value = "节点id")
    private String id;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    private String label;

    /**
     * 级别
     */
    @ApiModelProperty(value = "级别")
    private String level;


    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect(PowerTreeVO power){
        this.id = power.getPowerId();
        this.label = power.getPowerName();
        this.children = power.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    public TreeSelect(DeptTreeVO dept){
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.level = dept.getDeptLevel();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
