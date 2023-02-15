package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.pojo.entity.SysOperLog;
import com.Bibo.system.model.pojo.dto.LogListDTO;
import com.Bibo.system.model.pojo.vo.LogListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
public interface ISysOperLogService extends IService<SysOperLog> {
    /**
     * 保存系统日志记录
     * @param sysOperLog 日志信息
     */
    public void saveSysLog(SysOperLog sysOperLog);

    /**
     * 条件分页查询日志列表
     * @param logListDTO 日志条件
     * @return 结果
     */
    public IPage<LogListVO> selectLogPageList(LogListDTO logListDTO);

    /**
     * 清空日志
     */
    public void deleteAll();
}
