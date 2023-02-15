package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MessageListDTO对象",description = "消息查询列表对象")
public class MessageListDTO  extends PageRequest {

    /**
     * 消息标题
     */
    @ApiModelProperty("消息标题")
    private String title;

    /**
     * 发送人
     */
    @ApiModelProperty("发送人")
    private String senderName;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;

}
