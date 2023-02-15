package com.Bibo.system.model.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysCustomFieldListDto",description = "自定义表单字段对象")
public class SysCustomFieldListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "表单id（父级id）")
    private String formId;

    @ApiModelProperty(value = "自定义字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段类型：text文本；date日期；number数字")
    private String fieldType;

    @ApiModelProperty(value = "是否查询条件：1是0否")
    private Integer isCondition;

    @ApiModelProperty(value = "是否表单编辑条件：1是0否")
    private Integer isEdit;

    @ApiModelProperty(value = "是否表单展示条件：1是0否")
    private Integer isShow;

    @ApiModelProperty(value = "字段描述")
    private String description;

    @ApiModelProperty(value = "字段属性：json数据，下拉菜单存放下拉内容，选项内容")
    private String fieldsAttr;

    @ApiModelProperty(value = "展示排序")
    private Integer showSort;

    @ApiModelProperty(value = "条件排序")
    private Integer conditionSort;

    @ApiModelProperty(value = "编辑排序")
    private Integer editSort;

    @ApiModelProperty(value = "是否必填：1是0否")
    private Integer isRequired;

    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    @ApiModelProperty(value = "是否删除：1是0否")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否启用：1是0否")
    private Integer status;

    @ApiModelProperty(value = "层级")
    private Integer level;


}
