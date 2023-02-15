package com.Bibo.system.model.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_message")
public class SysMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //接收方姓名
    private String receiveName;
    //接收方用户id
    private String receiveId;
    //消息内容
    private String content;
    //消息标题
    private String title;
    //消息读取状态：1=未读；2=已读；
    private Integer readStatus;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //发送方id
    private String sendId;
    //发送方姓名
    private String sendName;
    //发送时间
    private Date sendTime;
    //模板id
    private String templateId;
    //状态：1=启用；2=停用
    private Integer status;
    //删除状态：1=未删；2=已删
    private Integer deleteStatus;



}
