package com.Bibo.system.model.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xdh
 * @since 2021-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysKeyManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    //使用部门
    private String depart;
    //使用描述
    private String useDescription;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;



}
