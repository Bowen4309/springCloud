package com.Bibo.system.model.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysTagType {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private String parentId;
    private String parentName;
    private Date createTime;
    private String createUser;
    private String level;
    private Integer index;

}
