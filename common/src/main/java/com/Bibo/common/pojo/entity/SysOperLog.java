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
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysOperLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String operId;

    /**
     * 日志内容
     */
    private String logInfo;

    /**
     * 操作类型（0其他 1新增 2修改 3删除）
     */
    private String operType;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 操作状态（0正常 1异常）
     */
    private String operStatus;

    /**
     * 操作人
     */
    private String operName;

    /**
     * 操作请求方式
     */
    private String operMethod;

    /**
     * 模块标题
     */
    private String operTitle;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 请求方法
     */
    private String method;


    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}
