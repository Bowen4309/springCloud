package com.Bibo.system.model.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xdh
 * @since 2021-09-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "KeyManageDTO对象",description = "密钥管理入参")
public class KeyManageReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    //使用部门
    @ApiModelProperty("使用部门")
    private String depart;

    //使用描述
    @ApiModelProperty("使用描述")
    private String useDescription;




}
