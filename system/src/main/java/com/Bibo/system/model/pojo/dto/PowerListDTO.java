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
 * @CreateTime: 2021/10/26 19:48
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PowerListDTO对象",description = "权限扩展列表对象")
public class PowerListDTO  extends PageRequest implements Serializable {

    /**
     * 名称
     */
    @ApiModelProperty(example = "测试", value = "名称")
    private String powerName;

//    /**
//     * 是否显示（0显示 1隐藏）
//     */
//    @ApiModelProperty(example = "0", value = "是否显示（0显示 1隐藏）")
//    private String visible;
//
//    /**
//     * 状态(0正常 1停用 2删除)
//     */
//    @ApiModelProperty(example = "1", value = "状态(0正常 1停用 2删除)")
//    private String status;
}
