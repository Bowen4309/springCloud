package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: CGF
 * @CreateTime: 2021/10/27 9:53
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DictListDTO对象",description = "字典查询列表对象")
public class DictListDTO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典标签
     */
    @ApiModelProperty(example = "男", value = "字典标签")
    private String dictLabel;

//    /**
//     * 字典类型
//     */
//    @ApiModelProperty(example = "用户性别", value = "字典类型")
//    private String dictType;

}
