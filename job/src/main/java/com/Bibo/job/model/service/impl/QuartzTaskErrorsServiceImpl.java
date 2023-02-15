package com.Bibo.job.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.job.model.entity.QuartzTaskErrors;
import com.Bibo.job.model.mapper.QuartzTaskErrorsMapper;
import com.Bibo.job.model.service.IQuartzTaskErrorsService;
import org.springframework.stereotype.Service;

@Service
public class QuartzTaskErrorsServiceImpl extends ServiceImpl<QuartzTaskErrorsMapper, QuartzTaskErrors> implements IQuartzTaskErrorsService {

    @Override
    public Integer addTaskErrorRecord(QuartzTaskErrors quartzTaskErrors) {
        return this.baseMapper.insert(quartzTaskErrors);
    }

    @Override
    public QuartzTaskErrors detailTaskErrors(String recordId) {
        return this.baseMapper.getErrorByRecordId(recordId);
    }
}
