package com.Bibo.system.model.pojo.dto;

import com.Bibo.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SysMessageTemplateListReqDTO",description = "消息模板列表请求对象")
public class SysMessageTemplateListReqDTO  extends PageRequest {


    //接收方类型：1=个人；2=部门
    @ApiModelProperty(value = "接收方类型：1=个人；2=部门")
    private Integer receiveType;
    //用户id或部门id
    @ApiModelProperty(value = "接收方名称")
    private String receiveName;
    //消息内容
    @ApiModelProperty(value = "消息内容")
    private String content;
    //消息标题
    @ApiModelProperty(value = "消息标题")
    private String title;
    //消息状态：1=草稿；2=启用；3=停用
    @ApiModelProperty(value = "消息状态：1=草稿；2=启用；3=停用")
    private Integer status;
//    //发送方id
//    @ApiModelProperty(value = "发送方id")
//    private String sendId;
    //发送方姓名
    @ApiModelProperty(value = "发送方姓名")
    private String sendName;
//    //发送时间
//    @ApiModelProperty(value = "发送时间")
//    private Date sendTime;


}
