package com.Bibo.job.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "quartz_task_informations", schema = "public")
public class QuartzTaskInformations implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键ID,修改时必传")
    private String id;

    @ApiModelProperty(value = "版本号")
    @TableField(value = "version")
    private Integer version;

    @ApiModelProperty(value = "任务编号")
    @TableField(value = "task_no")
    private String taskNo;

    @ApiModelProperty(value = "任务名称")
    @TableField(value = "task_name")
    private String taskName;

    @ApiModelProperty(value = "定时规则表达式")
    @TableField(value = "scheduler_rule")
    private String schedulerRule;

    @ApiModelProperty(value = "冻结状态")
    @TableField(value = "frozen_status")
    private String frozenStatus;

    @ApiModelProperty(value = "执行方")
    @TableField(value = "executor_no")
    private String executorNo;

    @ApiModelProperty(value = "冻结时间")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    @TableField(value = "frozen_time")
    private Timestamp frozenTime;

    @ApiModelProperty(value = "解冻时间")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    @TableField(value = "unfrozen_time")
    private Timestamp unfrozenTime;

    @ApiModelProperty(value = "创建时间")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    @TableField(value = "create_time")
    private Timestamp createTime;

    @ApiModelProperty(value = "最近修改时间")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    @TableField(value = "last_modify_time")
    private Timestamp lastModifyTime;

    @ApiModelProperty(value = "发送方式")
    @TableField(value = "send_type")
    private String sendType;

    @ApiModelProperty(value = "请求地址")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty(value = "执行参数")
    @TableField(value = "execute_param")
    private String executeParam;

    @ApiModelProperty(value = "timeKey")
    @TableField(value = "time_key")
    private String timeKey;


}
