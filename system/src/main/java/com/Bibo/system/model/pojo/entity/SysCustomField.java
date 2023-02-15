package com.Bibo.system.model.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_custom_field")
public class SysCustomField implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 表单id
     */
    private String formId;
    /**
     * 自定义字段名称
     */
    private String fieldName;
    /**
     * 字段类型：字段类型：radio单选；checkbox多选；input输入框；select选择器；datetime日期时间选择器；upload上传；dragMarker坐标拾取
     */
    private String fieldType;
    /**
     * 是否查询条件：1是0否
     */
    private Integer isCondition;
    /**
     * 是否表单编辑条件：1是0否
     */
    private Integer isEdit;
    /**
     * 是否表单展示条件：1是0否
     */
    private Integer isShow;
    /**
     * 字段描述
     */
    private String description;
    /**
     * 字段属性：json数据，下拉菜单存放下拉内容，选项内容
     */
    private String fieldsAttr;
    /**
     * 展示排序
     */
    private Integer showSort;
    /**
     * 条件排序
     */
    private Integer conditionSort;
    /**
     * 编辑排序
     */
    private Integer editSort;
    /**
     * 是否必填：1是0否
     */
    private Integer isRequired;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 是否删除：1是0否
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否启用：1是0否
     */
    private Integer status;


}
