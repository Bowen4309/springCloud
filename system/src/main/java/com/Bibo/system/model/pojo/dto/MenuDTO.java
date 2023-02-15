package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "sysMenu对象",description = "菜单对象")
public class MenuDTO implements Serializable{


    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", name = "id")
    private String id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", name = "name")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", name = "icon")
    private String icon;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "creatTime")
    private LocalDate creatTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "creatName")
    private String creatName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;
}
