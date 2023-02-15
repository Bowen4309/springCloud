package com.Bibo.common.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BaseVO {

    /**
     * 状态(0正常 1停用 2删除)
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;


    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
