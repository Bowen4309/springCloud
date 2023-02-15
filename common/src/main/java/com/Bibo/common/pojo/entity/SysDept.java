package com.Bibo.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.Bibo.common.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class SysDept extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 上级部门
     */
    private String parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 行政区域
     */
    private String administrative;

    /**
     * 部门地址
     */
    private String deptAddress;

    /**
     * 部门全称
     */
    private String deptFullName;

    /**
     * 印章名称
     */
    private String sealName;

    /**
     * 部门级别
     */
    private String deptLevel;

    /**
     * 部门处理业务 1-科技 2-车管 3-驾管 4-违法 5-事故 6-剧毒品 7-其他
     */
    private String deptBusinessHand;

    /**
     * 业务类别 20位 1-秩序 2-公路 3-事故 4-车驾管 5-高速 6-队伍建设 6-宣教 8-法制 9-科技 20-机构类型1-地市、城区 2-县市或公路 3-高速
     */
    private String deptBusinessType;

    /**
     * 负责人
     */
    private String deptHeader;

    /**
     * 联系人
     */
    private String deptContact;

    /**
     * 联系电话
     */
    private String deptContactPhone;

    /**
     * 上级车驾业务指导管理部门
     */
    private String horseParent;

    /**
     * 上级违法业务指导管理部门
     */
    private String illegalParent;

    /**
     * 上级事故业务指导管理部门
     */
    private String accidentParent;

    /**
     * 隶属关系 1-机关2-直属3-业务指导
     */
    private String subordinateRelation;
}
