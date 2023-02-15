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
@TableName("sys_custom_form")
public class SysCustomForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 名称
     */
    private String name;
    /**
     * 创建者用户id
     */
    private String createUserId;
    /**
     * 创建者用户姓名
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 排序
     */
    private Integer showSort;
    /**
     * 级别（1级，2级）
     */
    private Integer level;
    /**
     * 父级id
     */
    private String parentId;
    /**
     * 状态（1启用0停止）
     */
    private Integer status;
    /**
     * 是否删除（1是0否）
     */
    private Integer isDelete;


}
