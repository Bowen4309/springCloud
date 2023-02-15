package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
public class KeyManageResDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    //使用部门
    @ApiModelProperty("使用部门")
    private String depart;

    //使用描述
    @ApiModelProperty("使用描述")
    private String useDescription;

    //创建时间
    @ApiModelProperty("创建时间")
    private Date createTime;

    //修改时间
    @ApiModelProperty("修改时间")
    private Date updateTime;




}
