package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "DictDataDTO对象",description = "字典数据对象")
public class DictDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    @ApiModelProperty(example = "1", value = "字典id(编辑时必传)")
    private String dictId;

    /**
     * 字典名称
     */
    @ApiModelProperty(example = "test", value = "字典名称",required = true)
    private String dictName;

    /**
     * 字典标签
     */
    @ApiModelProperty(example = "测试", value = "字典标签",required = true)
    private String dictLabel;

    /**
     * 字典键值
     */
    @ApiModelProperty(example = "1", value = "字典键值",required = true)
    private String dictValue;

    /**
     * 字典类型
     */
    @ApiModelProperty(example = "sys_test", value = "字典类型",required = true)
    private String dictType;

    /**
     * 是否默认(0为默认)
     */
    @ApiModelProperty(example = "1", value = "是否默认(0为默认)")
    private String isDefault;


    @ApiModelProperty(example = "1", value = "排序号",required = true)
    private Integer dictSort;

    @ApiModelProperty(example = "1", value = "来源",required = true)
    private String type;
}
