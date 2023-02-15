package com.Bibo.system.model.pojo.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class LogListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String operId;

    /**
     * 日志内容
     */
    @ApiModelProperty(value = "日志内容")
    private String logInfo;

    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型")
    private String operType;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date operTime;

    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    private String operStatus;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operName;

    /**
     * 操作请求方式
     */
    @ApiModelProperty(value = "操作请求方式")
    private String operMethod;

    /**
     * 模块标题
     */
    @ApiModelProperty(value = "模块标题")
    private String operTitle;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String operParam;

    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    private String method;

}
