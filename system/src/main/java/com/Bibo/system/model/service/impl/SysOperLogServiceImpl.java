package com.Bibo.system.model.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.pojo.entity.SysOperLog;
import com.Bibo.system.model.mapper.SysOperLogMapper;
import com.Bibo.system.model.pojo.dto.LogListDTO;
import com.Bibo.system.model.pojo.vo.LogListVO;
import com.Bibo.system.model.service.ISysOperLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

    /**
     * 保存系统日志记录
     * @param sysOperLog 日志信息
     */
    @Override
    @Async
    public void saveSysLog(SysOperLog sysOperLog) {
        this.baseMapper.insert(sysOperLog);
    }

    /**
     * 条件分页查询日志列表
     * @param logListDTO 日志条件
     * @return 结果
     */
    @Override
    public IPage<LogListVO> selectLogPageList(LogListDTO logListDTO){
        Page<LogListVO> page = new Page(logListDTO.getPageNum(), logListDTO.getPageSize());
        return this.baseMapper.selectLogPageList(page, logListDTO);
    }

    /**
     * 清空日志
     */
    @Override
    public void deleteAll() {
        this.baseMapper.deleteAll();
    }
}
