package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysMessageDTO",description = "用户消息对象")
public class SysMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;
    //接收方姓名
    @ApiModelProperty(value = "接收方姓名")
    private String receiveName;
    //接收方用户id
    @ApiModelProperty(value = "接收方用户id")
    private String receiveId;
    //消息内容
    @ApiModelProperty(value = "消息内容")
    private String content;
    //消息标题
    @ApiModelProperty(value = "消息标题")
    private String title;
    //消息读取状态：1=未读；2=已读；
    @ApiModelProperty(value = "消息读取状态：1=未读；2=已读；")
    private Integer readStatus;
    //创建时间
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    //修改时间
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    //发送方id
    @ApiModelProperty(value = "发送方id")
    private String sendId;
    //发送方姓名
    @ApiModelProperty(value = "发送方姓名")
    private String sendName;
    //发送时间
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;
    //模板id
    @ApiModelProperty(value = "模板id")
    private String templateId;
    //状态：1=启用；2=停用
    @ApiModelProperty(value = "状态：1=启用；2=停用")
    private Integer status;



}
