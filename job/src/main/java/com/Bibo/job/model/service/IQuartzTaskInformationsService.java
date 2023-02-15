package com.Bibo.job.model.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.job.model.dto.TaskListDTO;
import com.Bibo.job.model.entity.QuartzTaskInformations;

import java.util.List;

public interface IQuartzTaskInformationsService extends IService<QuartzTaskInformations> {

    IPage<QuartzTaskInformations> getTaskList(TaskListDTO dto);

    Integer selectCountByTaskNo(String taskNo);

    QuartzTaskInformations getTaskByTaskNo(String taskNo);

    List<QuartzTaskInformations> getUnfrozenTasks(String status);

}
