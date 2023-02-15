package com.Bibo.system.model.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "MessagePageListVO对象",description = "消息通知查询返回列表对象")
public class MessagePageListVO {

    /**
     * 消息标题
     */
    @ApiModelProperty(value = "消息标题")
    private String title;

    /**
     * 发送人
     */
    @ApiModelProperty(value = "发送人")
    private String sender;


    /**
     * 发送人
     */
    @ApiModelProperty(value = "发送人")
    private String senderName;

    /**
     * 查看状态
     */
    @ApiModelProperty(value = "查看状态")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
}
