package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.pojo.entity.SysDictData;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "SysDictDataDto对象",description = "左侧目录封装类")
public class SysDictDataDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
//    private String dictName;

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
//    private String dictType;
//
//    /**
//     * 是否默认(0为默认)
//     */
//    private String isDefault;

    /**
     * 第二层数据集合
     */
    List<SysDictData> datas;


}
