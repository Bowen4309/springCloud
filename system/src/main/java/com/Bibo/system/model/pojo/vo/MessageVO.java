package com.Bibo.system.model.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "MessageVO对象",description = "消息详细对象")
public class MessageVO {

    /**
     * 消息ID
     */
    @ApiModelProperty(value = "消息ID")
    private String messageId;

    /**
     * 消息标题
     */
    @ApiModelProperty(value = "消息标题")
    private String title;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String content;

    /**
     * 发送人
     */
    @ApiModelProperty(value = "发送人")
    private String senderName;

    /**
     * 接受者：0所有用户 1下级全部 2同部门
     */
    @ApiModelProperty(value = "接受者：0所有用户 1下级全部 2同部门")
    private String receiver;


    /**
     * 状态
     */
    @ApiModelProperty(value = "状态 0正常 2删除")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
}
