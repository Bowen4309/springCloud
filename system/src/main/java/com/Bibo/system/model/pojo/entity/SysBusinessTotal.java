package com.Bibo.system.model.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysBusinessTotal {

    //@TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private String type;
    private String level;
//    private String parentId;
    private String parentName;
    private String status;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String showType;
    private String key;
    private String dataType;
    private String timeType;
    private String timeNum;
    private String log;
    private String showPicture;
    private String choose;
    private String createUser;

}
