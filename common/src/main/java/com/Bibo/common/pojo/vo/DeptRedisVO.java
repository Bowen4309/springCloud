package com.Bibo.common.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class DeptRedisVO {

    /**
     * 主键id
     */
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
     * 业务类别 20位 1-秩序 2-公路 3-事故 4-车驾管 5-高速 6-队伍建设 6-宣教 8-法制 9-科技 20-机构类型1-地市、城区 2-县市或公路 3-高速
     */
    private String deptBusinessType;

    /**
     * 子部门id拼接sql
     */
    private String childStrSql;

}
