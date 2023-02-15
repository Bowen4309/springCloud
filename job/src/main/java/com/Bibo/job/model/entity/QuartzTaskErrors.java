package com.Bibo.job.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "quartz_task_errors", schema = "public")
public class QuartzTaskErrors implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "任务执行记录ID")
    private String taskExecuteRecordId;

    @ApiModelProperty(value = "信息关键字")
    private String errorKey;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Timestamp lastModifyTime;

    @ApiModelProperty(value = "信息内容")
    private String errorValue;




}
