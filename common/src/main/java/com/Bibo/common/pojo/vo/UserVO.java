package com.Bibo.common.pojo.vo;

import com.Bibo.common.annotation.FiledConverted;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: CGF
 * @CreateTime: 2021/10/27 19:03
 * @Description:
 */
@Data
@ApiModel(value = "UserVO对象",description = "用户详情返回对象")
public class UserVO {

    /**
     * id
     */
    @ApiModelProperty(example = "id", value = "用户id")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty(example = "lisi", value = "用户名", required = true)
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty(example = "123456", value = "用户密码")
    private String password;

    /**
     * 用户标识（用于绑定）
     */
    @ApiModelProperty(value = "用户标识")
    private String userCode;

    /**
     * 警号
     */
    @ApiModelProperty(value = "警号")
    private String policeCode;

    /**
     * 部门id
     */
    @ApiModelProperty(example = "1", value = "部门id", required = true)
    private String deptId;

    /**
     * 性别
     */
    @ApiModelProperty(example = "1", value = "性别")
    private String sex;

    /**
     * 上级领导
     */
    @ApiModelProperty(example = "1", value = "上级领导")
    private String leaderId;

    /**
     * 状态(0正常 1停用 2删除)
     */
    @FiledConverted(dictType = "sys_user_sex",getCodeOrName = 0)
    @ApiModelProperty(example = "0", value = "状态(0正常 1停用 2删除)")
    private String status;

    /**
     * 六位管理部门+六位日期+六位序列
     */
    @ApiModelProperty(value = "code")
    private String code;

    /**
     * 警员类型：1交管；2民航；3铁路；4港口；5厂矿
     */
    @FiledConverted(dictType = "sys_police_type",getCodeOrName = 0)
    @ApiModelProperty(value = "警员类型：1交管；2民航；3铁路；4港口；5厂矿")
    private String type;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String nickName;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    /**
     * 编制类型：1公安编；2事业编；3地方编
     */
    @FiledConverted(dictType = "sys_police_prepare_type",getCodeOrName = 0)
    @ApiModelProperty(value = "编制类型：1公安编；2事业编；3地方编")
    private String positionType;

    /**
     * 业务岗位：01城区管理执勤；02国省道执勤；03高速执勤；04县乡执勤；05事故处理；06车驾管理；07道路宣传；08科技管理；09其他
     */
    @FiledConverted(dictType = "sys_police_business_post",getCodeOrName = 0)
    @ApiModelProperty(value = "业务岗位")
    private String businessPost;

    /**
     * 事故处理级别：0无等级；1高级；2中级；3初级；
     */
    @FiledConverted(dictType = "sys_police_incident_handling_level",getCodeOrName = 0)
    @ApiModelProperty(value = "事故处理级别：0无等级；1高级；2中级；3初级")
    private String accidentHandleLevel;

    /**
     * 领导级别：D0总队领导；D1支队领导；D2大队领导；D3中队领导；ZZ 其他
     */
    @FiledConverted(dictType = "sys_police_leader_level",getCodeOrName = 0)
    @ApiModelProperty(value = "领导级别：D0总队领导；D1支队领导；D2大队领导；D3中队领导；ZZ 其他")
    private String leaderLevel;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private String birthday;

    /**
     * 警衔
     */
    @ApiModelProperty(value = "警衔")
    private String policeJob;

    /**
     * 职级
     */
    @ApiModelProperty(value = "职级")
    private String policePosition;

    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    /**
     * 入党团时间
     */
    @ApiModelProperty(value = "入党团时间")
    private String joinGroupTime;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private String politicalAffiliation;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    private String nation;

    /**
     * 学历
     */
    @ApiModelProperty(value = "学历")
    private String education;

    /**
     * 专业
     */
    @ApiModelProperty(value = "专业")
    private String specialty;

    /**
     * 岗位职位级别
     */
    @ApiModelProperty(value = "岗位职位级别")
    private String jobLevel;

    /**
     * 入队时间
     */
    @ApiModelProperty(value = "入队时间")
    private String joinTime;

    /**
     * 参加工作时间
     */
    @ApiModelProperty(value = "参加工作时间")
    private String joinWorkTime;

    /**
     * 任现职时间
     */
    @ApiModelProperty(value = "任现职时间")
    private String workTime;

    /**
     * 家庭住址
     */
    @ApiModelProperty(value = "家庭住址")
    private String address;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    private String phone;

    /**
     * 办公电话
     */
    @ApiModelProperty(value = "办公电话")
    private String workPhone;

    /**
     * 宅电
     */
    @ApiModelProperty(value = "宅电")
    private String placePhone;

    /**
     * 记录状态
     */
    @ApiModelProperty(value = "记录状态")
    private String recordStatus;

    /**
     * 角色对象
     */
    @ApiModelProperty(value = "角色对象")
    private List<RoleListVO> roleList;

    /**
     * 角色id列表
     */
    @ApiModelProperty(value = "角色id列表")
    private String[] roleIds;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private DeptListVO dept;

    /**
     * 角色ID
     */
    @ApiModelProperty(name = "角色ID")
    private String roleId;

    /**
     * 上级领导名称
     */
    @ApiModelProperty(value = "上级领导名称")
    private String leaderName;

    @ApiModelProperty(value = "网格id")
    private String gridId;

    @ApiModelProperty(value = "网格名称")
    private String gridName;

}
