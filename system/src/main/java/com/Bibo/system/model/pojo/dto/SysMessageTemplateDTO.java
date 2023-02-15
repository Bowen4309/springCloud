package com.Bibo.system.model.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "SysMessageTemplateDTO",description = "消息模板对象")
public class SysMessageTemplateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;
    //接收方类型：1=个人；2=部门
    @ApiModelProperty(value = "接收部门Id，多个逗号隔开")
    private String receiveDeptId;
    //用户id或部门id
    @ApiModelProperty(value = "接收用户id，多个逗号隔开")
    private String receiveUserId;
    //消息内容
    @ApiModelProperty(value = "消息内容")
    private String content;
    //消息标题
    @ApiModelProperty(value = "消息标题")
    private String title;
    //消息状态：1=草稿；2=启用；3=停用
    @ApiModelProperty(value = "消息状态：1=启用；2=停用")
    private Integer status;
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
    //接收方用户姓名
    @ApiModelProperty(value = "接收方用户姓名，多个逗号隔开")
    private String receiveUserName;
    //接收方部门名称
    @ApiModelProperty(value = "接收方部门名称，多个逗号隔开")
    private String receiveDeptName;


}
