package com.Bibo.job.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Bibo.job.model.entity.QuartzTaskInformations;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QuartzTaskInformationsMapper extends BaseMapper<QuartzTaskInformations> {

    @Select("select count(1) from public.quartz_task_informations where task_no=#{taskNo}")
    Integer selectCountByTaskNo(@Param("taskNo") String taskNo);

    @Select("select * from public.quartz_task_informations where task_no=#{taskNo}")
    QuartzTaskInformations getTaskByTaskNo(@Param("taskNo") String taskNo);

    @Select("select * from public.quartz_task_informations where frozen_status = #{frozenStatus}")
    List<QuartzTaskInformations> getUnfrozenTasks(@Param("frozenStatus") String status);

}
