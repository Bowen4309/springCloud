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
@TableName("sys_message_template")
public class SysMessageTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //部门id
    private String receiveDeptId;
    //用户id
    private String receiveUserId;
    //消息内容
    private String content;
    //消息标题
    private String title;
    //消息状态：1=启用；2=停用
    private Integer status;
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
    //接收方用户姓名
    private String receiveUserName;
    //接收方部门名称
    private String receiveDeptName;



}
