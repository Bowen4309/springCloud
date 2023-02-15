package com.Bibo.system.model.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 数据字典
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TfDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String ixnm;

    /**
     * 字典代码
     */
    private String desc0;

    /**
     *字典名称
     */
    private String desc1;

    /**
     * 字典描述
     */
    private String desc2;

    /**
     * 字典类型
     */
    private String dety;

    /**
     * 字典类型中文名
     */
    private String detyCn;

}
