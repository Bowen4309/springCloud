package com.Bibo.system.model.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysCustomFormFieldInsertDto",description = "自定义表单插入对象")
public class SysCustomFormFieldInsertDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "层级：1;2;3;",required = true)
    private Integer level;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "名称")
    private String fieldName;

    @ApiModelProperty(value = "字段类型：radio单选；checkbox多选；input输入框；select选择器；datetime日期时间选择器；upload上传；dragMarker坐标拾取")
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

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除：1是0否")
    private Integer isDelete;

    @ApiModelProperty(value = "状态（1启用0停止）")
    private Integer status;

}
