package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "SysCustomFormTwoListDto",description = "自定义表单列表对象")
public class SysCustomFormTwoListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "名称")
    private String fieldName;

    @ApiModelProperty(value = "主键id")
    private String createUserId;

    @ApiModelProperty(value = "创建者用户id")
    private String createUserName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "排序")
    private Integer showSort;

    @ApiModelProperty(value = "级别（1级，2级）")
    private Integer level;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "状态（1启用0停止）")
    private Integer status;

    @ApiModelProperty(value = "是否删除（1是0否）")
    private Integer isDelete;

    @ApiModelProperty(value = "字段集合")
    List<SysCustomFieldListDto> fields;

}
