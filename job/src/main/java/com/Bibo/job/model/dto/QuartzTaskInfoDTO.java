package com.Bibo.job.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@ApiModel(value = "QuartzTaskInfoDTO对象", description = "定时任务信息对象")
public class QuartzTaskInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty(value = "版本号：需要乐观锁控制")
    private Integer version;

    @ApiModelProperty(value = "任务编号")
    private String taskNo;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "定时规则表达式")
    private String schedulerRule;

    @ApiModelProperty(value = "冻结状态")
    private String frozenStatus;

    @ApiModelProperty(value = "执行方")
    private String executorNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "冻结时间")
    private Timestamp frozenTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "解冻时间")
    private Timestamp unfrozenTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最近修改时间")
    private Timestamp lastModifyTime;

    @ApiModelProperty(value = "发送方式")
    private String sendType;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "执行参数")
    private String executeParam;

    @ApiModelProperty(value = "timeKey")
    private String timeKey;


}
