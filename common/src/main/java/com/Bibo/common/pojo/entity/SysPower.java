package com.Bibo.common.pojo.entity;

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
 * @since 2021-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysPower  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String powerId;

    /**
     * 类型(1菜单 2目录 3按钮)
     */
    private String powerType;

    /**
     * 名称
     */
    private String powerName;

    /**
     * 是否显示（0显示 1隐藏）
     */
    private String visible;

    /**
     * 编码
     */
    private String code;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件名称
     */
    private String componentName;


    /**
     * 组件引用地址
     */
    private String component;


    /**
     * 父id
     */
    private String parentId;


    /**
     * 系统类型 1后台 2网格
     */
    private String systemType;

    /**
     * 父节列表
     */
    private String ancestors;

    /**
     * 备注
     */
    private Integer remark;


    /**
     * 状态(0正常 1停用 2删除)
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改者
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;


}
