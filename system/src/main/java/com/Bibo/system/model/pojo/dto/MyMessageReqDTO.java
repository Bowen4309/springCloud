package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "MyMessageReqDTO",description = "查询我的消息请求对象")
public class MyMessageReqDTO extends PageRequest {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("读取状态：1=未读；2=已读")
    private Integer status;


}
