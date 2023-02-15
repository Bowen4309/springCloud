package com.Bibo.job.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.job.model.entity.QuartzTaskRecords;

import java.util.List;

public interface IQuartzTaskRecordsService extends IService<QuartzTaskRecords> {

    int insert(QuartzTaskRecords record);

    int updateByPrimaryKeySelective(QuartzTaskRecords record);

    List<QuartzTaskRecords> getTaskRecordsByTaskNo(String taskNo);

}
