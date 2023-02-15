package com.Bibo.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.Bibo.common.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictData  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableId(type = IdType.ASSIGN_UUID)
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

    private String type;

}
