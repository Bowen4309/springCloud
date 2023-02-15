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
@TableName("sys_custom_field_value")
public class SysCustomFieldValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 字段主键id
     */
    private String fieldId;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 表单主键id
     */
    private String formId;
    /**
     * 自定义字段值：文本
     */
    private String varcharValue;
    /**
     * 自定义字段值：数字
     */
    private Integer intValue;
    /**
     * 自定义字段值：浮点数
     */
    private Double floatValue;
    /**
     * 自定义字段值：日期
     */
    private Date datetimeValue;
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
     * 默认行号
     */
    private Integer defaultRowNo;
    /**
     * 展示排序字段
     */
    private Integer showSort;



}
