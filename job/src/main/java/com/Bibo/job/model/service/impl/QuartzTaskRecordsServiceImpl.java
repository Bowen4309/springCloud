package com.Bibo.job.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.job.model.entity.QuartzTaskRecords;
import com.Bibo.job.model.mapper.QuartzTaskRecordsMapper;
import com.Bibo.job.model.service.IQuartzTaskRecordsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartzTaskRecordsServiceImpl extends ServiceImpl<QuartzTaskRecordsMapper, QuartzTaskRecords> implements IQuartzTaskRecordsService {
    @Override
    public int insert(QuartzTaskRecords record) {
        return this.baseMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(QuartzTaskRecords record) {
        return this.baseMapper.updateById(record);
    }

    @Override
    public List<QuartzTaskRecords> getTaskRecordsByTaskNo(String taskNo) {
        return this.baseMapper.getTaskRecordsByTaskNo(taskNo);
    }
}
