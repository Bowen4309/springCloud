package com.Bibo.job.model.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.Bibo.job.model.dto.TaskListDTO;
import com.Bibo.job.model.entity.QuartzTaskErrors;
import com.Bibo.job.model.entity.QuartzTaskInformations;
import com.Bibo.job.model.entity.QuartzTaskRecords;
import com.Bibo.job.model.vo.QuartzTaskRecordsVo;
import com.Bibo.common.response.Response;
import org.quartz.SchedulerException;

import java.util.List;

public interface IQuartzService {

    boolean addTask(QuartzTaskInformations quartzTaskInformations);

    IPage<QuartzTaskInformations> getTaskList(TaskListDTO dto);

    QuartzTaskInformations getTaskById(String id);

    boolean updateTask(QuartzTaskInformations quartzTaskInformations);

    Response startJob(String taskNo) throws SchedulerException;

    void initLoadOnlineTasks();

    void sendMessage(String message);

    QuartzTaskRecords addTaskRecords(String taskNo);

    Integer updateRecordById(Integer count, String id);

    Integer addTaskErrorRecord(String id, String errorKey, String errorValue);

    List<QuartzTaskRecordsVo> taskRecords(String taskNo);

    Response runTaskRightNow(String taskNo);

    QuartzTaskErrors detailTaskErrors(String recordId);
}
