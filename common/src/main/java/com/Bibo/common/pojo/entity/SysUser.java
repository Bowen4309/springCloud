package com.Bibo.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 警员用户姓名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 警号
     */
    private String policeCode;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 性别
     */
    private String sex;

    /**
     * 上级领导
     */
    private String leaderId;

    /**
     * 网格ID
     */
    private String gridId;


    /**
     * 六位管理部门+六位日期+六位序列
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 警员类型：1交管；2民航；3铁路；4港口；5厂矿
     */
    private String userType;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 编制类型：1公安编；2事业编；3地方编
     */
    private String positionType;

    /**
     * 业务岗位：01城区管理执勤；02国省道执勤；03高速执勤；04县乡执勤；05事故处理；06车驾管理；07道路宣传；08科技管理；09其他
     */
    private String businessPost;

    /**
     * 事故处理级别：0无等级；1高级；2中级；3初级；
     */
    private String accidentHandleLevel;

    /**
     * 领导级别：D0总队领导；D1支队领导；D2大队领导；D3中队领导；ZZ 其他
     */
    private String leaderLevel;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 警衔
     */
    private String policeJob;

    /**
     * 职级
     */
    private String policePosition;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 入党团时间
     */
    private String joinGroupTime;

    /**
     * 政治面貌
     */
    private String politicalAffiliation;

    /**
     * 民族
     */
    private String nation;

    /**
     * 学历
     */
    private String education;

    /**
     * 专业
     */
    private String specialty;

    /**
     * 岗位职位级别
     */
    private String jobLevel;

    /**
     * 入队时间
     */
    private String joinTime;

    /**
     * 参加工作时间
     */
    private String joinWorkTime;

    /**
     * 任现职时间
     */
    private String workTime;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 办公电话
     */
    private String workPhone;

    /**
     * 宅电
     */
    private String placePhone;

    /**
     * 记录状态
     */
    private String recordStatus;

    /**
     * 网格名称
     */
    private String gridName;

}
