package com.Bibo.job.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Bibo.job.model.entity.QuartzTaskErrors;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface QuartzTaskErrorsMapper extends BaseMapper<QuartzTaskErrors> {

    @Select("select * from quartz_task_errors where task_execute_record_id = #{recordId}")
    QuartzTaskErrors getErrorByRecordId(@Param("recordId") String recordId);

}
