package com.Bibo.job.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.Bibo.common.serializer.Date2LongSerializer;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "quartz_task_records", schema = "public")
public class QuartzTaskRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String taskNo;

    private String timeKeyValue;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Timestamp executeTime;

    private String taskStatus;

    private Integer failCount;

    private String failReason;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Timestamp createTime;

    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Timestamp lastModifyTime;

}
