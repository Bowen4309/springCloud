package com.Bibo.job.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;


@Data
@ApiModel(value = "QuartzTaskRecordsVo对象", description = "定时任务执行情况记录对象")
public class QuartzTaskRecordsVo
{

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "任务编号")
    private String taskNo;

    @ApiModelProperty(value = "执行时间格式值")
    private String timeKeyValue;

    @ApiModelProperty(value = "执行时间")
    private Timestamp executeTime;

    @ApiModelProperty(value = "任务状态")
    private String taskStatus;

    @ApiModelProperty(value = "失败统计数")
    private Integer failCount;

    @ApiModelProperty(value = "失败错误描述")
    private String failReason;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "最近修改时间")
    private Timestamp lastModifyTime;

//    @ApiModelProperty(value = "失败内容")
//    private QuartzTaskErrors failContent;

    @ApiModelProperty(value = "信息关键字")
    private String errorKey;

    @ApiModelProperty(value = "信息内容")
    private String errorValue;

    private Timestamp time;

}
