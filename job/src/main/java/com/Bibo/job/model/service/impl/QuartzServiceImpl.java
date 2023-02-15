package com.Bibo.job.model.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.Bibo.job.model.dto.TaskListDTO;
import com.Bibo.job.model.entity.QuartzTaskErrors;
import com.Bibo.job.model.entity.QuartzTaskInformations;
import com.Bibo.job.model.entity.QuartzTaskRecords;
import com.Bibo.job.model.job.QuartzMainJobFactory;
import com.Bibo.job.model.service.IQuartzTaskInformationsService;
import com.Bibo.job.model.service.IQuartzTaskRecordsService;
import com.Bibo.job.model.util.CommonUtil;
import com.Bibo.job.model.util.HttpClientUtil;
import com.Bibo.job.model.vo.QuartzTaskRecordsVo;
import com.Bibo.common.constant.ResponseEnum;
import com.Bibo.common.response.Response;
import com.Bibo.job.model.service.IQuartzService;
import com.Bibo.job.model.service.IQuartzTaskErrorsService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class QuartzServiceImpl implements IQuartzService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);
    private AtomicInteger atomicInteger;

    @Autowired
    private IQuartzTaskInformationsService quartzTaskInformationsService;

    @Autowired
    private IQuartzTaskRecordsService quartzTaskRecordsService;

    @Autowired
    private IQuartzTaskErrorsService quartzTaskErrorsService;

    @Autowired
    private SchedulerFactoryBean schedulerBean;

    /**
     * 分頁列表查询所有定时任务
     *
     * @return
     */
    @Override
    public IPage<QuartzTaskInformations> getTaskList(TaskListDTO dto) {
        return quartzTaskInformationsService.getTaskList(dto);
    }

    /**
     * 新增定时任务
     * @param quartzTaskInformations
     * @return
     */
    @Override
    public boolean addTask(QuartzTaskInformations quartzTaskInformations) {
        quartzTaskInformations.setCreateTime(new Timestamp(new Date().getTime()));
        if(StringUtils.isEmpty(quartzTaskInformations.getFrozenStatus())){
            quartzTaskInformations.setFrozenStatus(ResponseEnum.FROZEN.getMsg());
        }
        return quartzTaskInformationsService.save(quartzTaskInformations);
    }

    @Override
    public QuartzTaskInformations getTaskById(String id) {
        return quartzTaskInformationsService.getById(id);
    }

    @Override
    public boolean updateTask(QuartzTaskInformations quartzTaskInformations) {
        quartzTaskInformations.setLastModifyTime(new Timestamp(new Date().getTime()));
        return quartzTaskInformationsService.updateById(quartzTaskInformations);
    }

    /**
     * 启动 or 暂停定时任务
     *
     * @param taskNo
     * @return
     * @throws SchedulerException
     */
    @Override
    @Transactional
    public Response startJob(String taskNo) throws SchedulerException {
        QuartzTaskInformations quartzTaskInformation = quartzTaskInformationsService.getTaskByTaskNo(taskNo);
        if (quartzTaskInformation == null) {
            return Response.custom(ResponseEnum.NO_DATA);
        }
        String status = quartzTaskInformation.getFrozenStatus();
        Scheduler scheduler = schedulerBean.getScheduler();
        long currentTimeMillis = System.currentTimeMillis();
        QuartzTaskInformations task = new QuartzTaskInformations();
        task.setId(quartzTaskInformation.getId());
        task.setVersion(quartzTaskInformation.getVersion());
        //说明要暂停
        if (ResponseEnum.UNFROZEN.name().equals(status)) {
            scheduler.deleteJob(new JobKey(taskNo));
            task.setFrozenTime(new Timestamp(currentTimeMillis));
            task.setFrozenStatus(ResponseEnum.FROZEN.name());
            //说明要启动
        } else if (ResponseEnum.FROZEN.name().equals(status)) {
            scheduler.deleteJob(new JobKey(taskNo));
            this.schedule(quartzTaskInformation, scheduler);
            task.setUnfrozenTime(new Timestamp(currentTimeMillis));
            task.setFrozenStatus(ResponseEnum.UNFROZEN.name());
        }
        task.setLastModifyTime(new Timestamp(currentTimeMillis));
        quartzTaskInformationsService.updateById(task);
        logger.info("taskNo={},taskName={},scheduleRule={},任务{}成功", quartzTaskInformation.getTaskNo(), quartzTaskInformation.getTaskName(), quartzTaskInformation.getSchedulerRule(), ResponseEnum.FROZEN.name().equals(status) ? "启动" : "暂停");
        return Response.success();
    }

    /**
     * 初始化加载定时任务
     *
     * @throws Exception
     */
    @Override
    public void initLoadOnlineTasks() {
        List<QuartzTaskInformations> unnfrozenTasks = quartzTaskInformationsService.getUnfrozenTasks(ResponseEnum.UNFROZEN.name());
        if (CollectionUtils.isEmpty(unnfrozenTasks)) {
            logger.info("没有需要初始化加载的定时任务");
            return;
        }
        Scheduler scheduler = schedulerBean.getScheduler();
        for (QuartzTaskInformations unnfrozenTask : unnfrozenTasks) {
            try {
                this.schedule(unnfrozenTask, scheduler);
            } catch (Exception e) {
                logger.error("系统初始化加载定时任务:taskNo={},taskName={}失败原因exception={}", unnfrozenTask.getTaskNo(), unnfrozenTask.getTaskName(), e);
            }
        }
    }

    /**
     * 初始化加载定时任务
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.initLoadOnlineTasks();
    }

    @Override
    public QuartzTaskRecords addTaskRecords(String taskNo) {
        QuartzTaskRecords quartzTaskRecords = null;
        try {
            QuartzTaskInformations quartzTaskInformation = quartzTaskInformationsService.getTaskByTaskNo(taskNo);
            if (null == quartzTaskInformation || ResponseEnum.FROZEN.name().equals(quartzTaskInformation.getFrozenStatus())) {
                logger.info("taskNo={} not exist or status is frozen!");
                return null;
            }
            long currentTimeMillis = System.currentTimeMillis();
            QuartzTaskInformations task = new QuartzTaskInformations();
            task.setId(quartzTaskInformation.getId());
            task.setLastModifyTime(new Timestamp(currentTimeMillis));
            quartzTaskInformationsService.updateById(task);
            logger.info("taskNo={},taskName={}更新最后修改时间成功", quartzTaskInformation.getTaskNo(), quartzTaskInformation.getTaskName());

            quartzTaskRecords = new QuartzTaskRecords();
            quartzTaskRecords.setTaskNo(taskNo);
            quartzTaskRecords.setTimeKeyValue(quartzTaskInformation.getTimeKey());
            quartzTaskRecords.setExecuteTime(new Timestamp(currentTimeMillis));
            quartzTaskRecords.setTaskStatus(ResponseEnum.INIT.name());
            quartzTaskRecords.setFailCount(0);
            quartzTaskRecords.setFailReason("");
            quartzTaskRecords.setCreateTime(new Timestamp(currentTimeMillis));
            quartzTaskRecords.setLastModifyTime(new Timestamp(currentTimeMillis));
            quartzTaskRecordsService.insert(quartzTaskRecords);
            logger.info("taskNo={},taskName={}添加执行记录表成功", quartzTaskInformation.getTaskNo(), quartzTaskInformation.getTaskName());
        } catch (Exception ex) {
            logger.error("添加执行记录表异常exceptio={}", ex);
            return null;
        }
        return quartzTaskRecords;
    }

    @Override
    public Integer updateRecordById(Integer count, String id) {
        QuartzTaskRecords records = new QuartzTaskRecords();
        records.setId(id);
        records.setFailCount(count);
        records.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        if (count > 0) {
            records.setTaskStatus(ResponseEnum.ERROR.name());
        } else {
            records.setTaskStatus(ResponseEnum.SUCCESS.name());
        }
        return quartzTaskRecordsService.updateByPrimaryKeySelective(records);
    }


    @Override
    public Integer addTaskErrorRecord(String id, String errorKey, String errorValue) {
        QuartzTaskErrors taskErrors = new QuartzTaskErrors();
        taskErrors.setTaskExecuteRecordId(String.valueOf(id));
        taskErrors.setErrorKey(errorKey);
        taskErrors.setCreateTime(new Timestamp(System.currentTimeMillis()));
        taskErrors.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        taskErrors.setErrorValue(errorValue);
        return quartzTaskErrorsService.addTaskErrorRecord(taskErrors);
    }

    public void schedule(QuartzTaskInformations quartzTaskInfo, Scheduler scheduler) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(quartzTaskInfo.getTaskNo(), Scheduler.DEFAULT_GROUP);
        JobDetail jobDetail = JobBuilder.newJob(QuartzMainJobFactory.class).withDescription(quartzTaskInfo.getTaskName()).withIdentity(quartzTaskInfo.getTaskNo(), Scheduler.DEFAULT_GROUP).build();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("id", quartzTaskInfo.getId().toString());
        jobDataMap.put("taskNo", quartzTaskInfo.getTaskNo());
        jobDataMap.put("executorNo", quartzTaskInfo.getExecutorNo());
        jobDataMap.put("sendType", quartzTaskInfo.getSendType());
        jobDataMap.put("url", quartzTaskInfo.getUrl());
        jobDataMap.put("executeParam", quartzTaskInfo.getExecuteParam());
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartzTaskInfo.getSchedulerRule());
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withDescription(quartzTaskInfo.getTaskName()).withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        logger.info("taskNo={},taskName={},scheduleRule={} load to quartz success!", quartzTaskInfo.getTaskNo(), quartzTaskInfo.getTaskName(), quartzTaskInfo.getSchedulerRule());
    }

    /**
     * kafka推送消息
     *
     * @param message
     */
    @Override
    public void sendMessage(String message) {
        // kafkaTemplate.send(QUARTZ_TOPIC, message);
        logger.info("给kafka推送消息message={}成功", message);
    }

    @Override
    public List<QuartzTaskRecordsVo> taskRecords(String taskNo) {
        List<QuartzTaskRecords> quartzTaskRecords = quartzTaskRecordsService.getTaskRecordsByTaskNo(taskNo);
//        QuartzTaskRecordsVo recordsVo = null;
//        List<QuartzTaskRecordsVo> voList = new ArrayList<>();
//        for (QuartzTaskRecords quartzTaskRecord : quartzTaskRecords) {
//            recordsVo = new QuartzTaskRecordsVo();
//            BeanUtils.copyProperties(quartzTaskRecord, recordsVo);
//            recordsVo.setTime(new Timestamp(quartzTaskRecord.getLastModifyTime().getTime() - quartzTaskRecord.getCreateTime().getTime()));
//            voList.add(recordsVo);
//        }
        if(quartzTaskRecords != null && quartzTaskRecords.size() > 0){
            List<QuartzTaskRecordsVo> voList = quartzTaskRecords.stream().map(q -> {
                QuartzTaskRecordsVo recordsVo = new QuartzTaskRecordsVo();
                BeanUtils.copyProperties(q, recordsVo);
                //recordsVo.setTime(new Timestamp(q.getLastModifyTime().getTime() - q.getCreateTime().getTime()));
                QuartzTaskErrors quartzTaskErrors = detailTaskErrors(recordsVo.getId());
                if(quartzTaskErrors != null){
                    recordsVo.setErrorKey(quartzTaskErrors.getErrorKey());
                    recordsVo.setErrorValue(quartzTaskErrors.getErrorValue());
                }
                return recordsVo;
            }).collect(Collectors.toList());
            return voList;
        }
        return null;

    }

    /**
     * 立即运行一次定时任务
     *
     * @param taskNo
     * @return
     */
    @Override
    public Response runTaskRightNow(String taskNo) {
        atomicInteger = new AtomicInteger(0);
        QuartzTaskInformations quartzTaskInformations = quartzTaskInformationsService.getTaskByTaskNo(taskNo);
        if (quartzTaskInformations == null) {
            return Response.custom(ResponseEnum.NO_DATA);
        }
        String id = quartzTaskInformations.getId();
        String sendType = quartzTaskInformations.getSendType();
        String executorNo = quartzTaskInformations.getExecutorNo();
        String url = quartzTaskInformations.getUrl();
        String executeParameter = quartzTaskInformations.getExecuteParam();
        logger.info("定时任务被执行:taskNo={},executorNo={},sendType={},url={},executeParam={}", taskNo, executorNo, sendType, url, executeParameter);
        QuartzTaskRecords records = null;
        try {
            //保存定时任务的执行记录
            records = this.addTaskRecords(taskNo);
            if (null == records || !ResponseEnum.INIT.name().equals(records.getTaskStatus())) {
                logger.info("taskNo={}立即运行失--->>保存执行记录失败", taskNo);
                return Response.custom(ResponseEnum.RUN_NOW_FAIL);
            }

            if (ResponseEnum.HTTP.getMsg().equals(sendType)) {
                try {
//                    String s = HttpClientUtil.doPost(url, "text/json", executeParameter);
                    String s = HttpClientUtil.doGet(url);
                    logger.info(s);
                } catch (Exception ex) {
                    logger.error("");
                    atomicInteger.incrementAndGet();
                    throw ex;
                }
            }
//            else if (ResponseEnum.KAFKA.getMsg().equals(sendType)) {
//                try {
//                    String message = new StringBuffer(taskNo).append(":").append(executeParameter).toString();
//                    this.sendMessage(message);
//                } catch (Exception ex) {
//                    logger.error("");
//                    atomicInteger.incrementAndGet();
//                    throw ex;
//                }
//            }
        } catch (Exception ex) {
            logger.error("");
            atomicInteger.incrementAndGet();
            this.addTaskErrorRecord(records.getId().toString(), taskNo + ":" + ex.getMessage(), CommonUtil.getExceptionDetail(ex));
        }
        //更改record表的执行状态、最后修改时间、失败个数
        this.updateRecordById(atomicInteger.get(), records.getId());

        //更新taskinfo表的最后修改时间
        QuartzTaskInformations quartzTaskInformation = new QuartzTaskInformations();
        quartzTaskInformation.setId(id);
        quartzTaskInformation.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        this.updateTask(quartzTaskInformation);
        return Response.success();
    }

    @Override
    public QuartzTaskErrors detailTaskErrors(String recordId) {
        return quartzTaskErrorsService.detailTaskErrors(recordId);
    }

}
