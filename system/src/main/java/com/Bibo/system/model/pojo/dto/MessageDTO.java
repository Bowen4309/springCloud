package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "MessageDTO对象",description = "新增编辑消息详细对象")
public class MessageDTO {

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
     * 接受者：0所有用户 1同部门 2下级全部
     */
    @ApiModelProperty(value = "接受者：0所有用户 1同部门 2下级全部")
    private String receiver;

}
