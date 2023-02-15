package com.Bibo.job.model.job;


import com.Bibo.common.constant.ResponseEnum;
import com.Bibo.job.model.entity.QuartzTaskInformations;
import com.Bibo.job.model.entity.QuartzTaskRecords;
import com.Bibo.job.model.service.IQuartzService;
import com.Bibo.job.model.service.impl.QuartzServiceImpl;
import com.Bibo.job.model.util.ApplicationContextHolder;
import com.Bibo.job.model.util.CommonUtil;
import com.Bibo.job.model.util.HttpClientUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;


@DisallowConcurrentExecution
public class QuartzMainJobFactory implements Job {

    private static Logger logger = LoggerFactory.getLogger(QuartzMainJobFactory.class);

    private AtomicInteger atomicInteger;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        atomicInteger = new AtomicInteger(0);

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String id = jobDataMap.getString("id");
        String taskNo = jobDataMap.getString("taskNo");
        String executorNo = jobDataMap.getString("executorNo");
        String sendType = jobDataMap.getString("sendType");
        String url = jobDataMap.getString("url");
        String executeParameter = jobDataMap.getString("executeParameter");
        logger.info("定时任务被执行:taskNo={},executorNo={},sendType={},url={},executeParameter={}", taskNo, executorNo, sendType, url, executeParameter);
        IQuartzService quartzService = (QuartzServiceImpl) ApplicationContextHolder.getBean("quartzServiceImpl");
        QuartzTaskRecords records = null;
        try {
            //保存定时任务的执行记录
            records = quartzService.addTaskRecords(taskNo);
            if (null == records || !ResponseEnum.INIT.name().equals(records.getTaskStatus())) {
                logger.info("taskNo={}保存执行记录失败", taskNo);
                return;
            }

            if (ResponseEnum.HTTP.getMsg().equals(sendType)) {
                try {
//                    String result = HttpClientUtil.doPost(url, "text/json", executeParameter);
                    String result = HttpClientUtil.doGet(url);
                    logger.info("taskNo={},sendtype={}执行结果result{}", taskNo, sendType, result);
//                    if (StringUtils.isEmpty(result)) {
//                        throw new RuntimeException("taskNo=" + taskNo + "http方式返回null");
//                    }
                } catch (Exception ex) {
                    logger.error("");
                    throw ex;
                }
            } else if (ResponseEnum.KAFKA.getMsg().equals(sendType)) {
                try {
                    String message = new StringBuffer(taskNo).append(":").append(id).append(":").append(executeParameter).toString();
                    quartzService.sendMessage(message);
                    logger.info("taskNo={},sendtype={}推送至kafka成功", taskNo, sendType);
                } catch (Exception ex) {
                    logger.error("");
                    throw ex;
                }
            }
        } catch (Exception ex) {
            logger.error("");
            atomicInteger.incrementAndGet();
            quartzService.addTaskErrorRecord(records.getId().toString(), taskNo + ":" + ex.getMessage(), CommonUtil.getExceptionDetail(ex));
        }

        quartzService.updateRecordById(atomicInteger.get(), records.getId());
        QuartzTaskInformations quartzTaskInformation = new QuartzTaskInformations();
        quartzTaskInformation.setId(id);
        quartzTaskInformation.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
        quartzService.updateTask(quartzTaskInformation);
    }
}
