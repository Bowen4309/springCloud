package com.Bibo.job.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.job.model.entity.QuartzTaskErrors;

public interface IQuartzTaskErrorsService extends IService<QuartzTaskErrors> {

    Integer addTaskErrorRecord(QuartzTaskErrors quartzTaskErrors);

    QuartzTaskErrors detailTaskErrors(String recordId);


}
