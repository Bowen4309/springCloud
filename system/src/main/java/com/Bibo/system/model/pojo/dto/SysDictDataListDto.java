package com.Bibo.system.model.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysDictDataListDto对象",description = "数据字典列表封装类")
public class SysDictDataListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    private String dictId;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 是否默认(0为默认)
     */
    private String isDefault;


    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 修改者
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;



}
