package com.Bibo.job.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Bibo.job.model.entity.QuartzTaskRecords;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QuartzTaskRecordsMapper extends BaseMapper<QuartzTaskRecords> {

    @Select("select * from quartz_task_records where task_no = #{taskNo} order by create_time desc limit 20")
    List<QuartzTaskRecords> getTaskRecordsByTaskNo(@Param("taskNo") String taskNo);

}
