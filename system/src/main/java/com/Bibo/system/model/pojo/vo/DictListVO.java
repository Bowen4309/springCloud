package com.Bibo.system.model.pojo.vo;

import com.Bibo.common.pojo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DeptListVO对象",description = "部门查询返回列表对象")
public class DictListVO extends BaseVO {

    /**
     * 字典id
     */
    @ApiModelProperty(value = "字典id")
    private String dictId;

    /**
     * 字典排序
     */
    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;

    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    /**
     * 字典键值
     */
    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    /**
     * 是否默认(0为默认)
     */
    @ApiModelProperty(value = "是否默认(0为默认)")
    private String isDefault;

}
