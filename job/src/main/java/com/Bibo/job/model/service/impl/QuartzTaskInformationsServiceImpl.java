package com.Bibo.job.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.job.model.dto.TaskListDTO;
import com.Bibo.job.model.entity.QuartzTaskInformations;
import com.Bibo.job.model.mapper.QuartzTaskInformationsMapper;
import com.Bibo.job.model.service.IQuartzTaskInformationsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartzTaskInformationsServiceImpl extends ServiceImpl<QuartzTaskInformationsMapper, QuartzTaskInformations> implements IQuartzTaskInformationsService {

    @Override
    public IPage<QuartzTaskInformations> getTaskList(TaskListDTO dto) {
        IPage<QuartzTaskInformations> page = new Page<QuartzTaskInformations>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<QuartzTaskInformations> queryWrapper = new QueryWrapper<QuartzTaskInformations>();
        if(StringUtils.isNotBlank(dto.getTaskNo())){
            queryWrapper.like("task_no",dto.getTaskNo());
            //queryWrapper.eq("task_no",dto.getTaskNo());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<QuartzTaskInformations> page1 = this.page(page, queryWrapper);
        return page1;
    }


    @Override
    public Integer selectCountByTaskNo(String taskNo) {
        return this.baseMapper.selectCountByTaskNo(taskNo);
    }

    @Override
    public QuartzTaskInformations getTaskByTaskNo(String taskNo) {
        return this.baseMapper.getTaskByTaskNo(taskNo);
    }

    @Override
    public List<QuartzTaskInformations> getUnfrozenTasks(String status) {
        return this.baseMapper.getUnfrozenTasks(status);
    }
}
