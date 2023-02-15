package com.Bibo.system.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Bibo.common.pojo.entity.SysOperLog;
import com.Bibo.system.model.pojo.dto.LogListDTO;
import com.Bibo.system.model.pojo.vo.LogListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    /**
     * 条件分页查询日志列表
     * @param logListDTO 日志条件
     * @return 结果
     */
    @Select("<script>" +
            "SELECT oper_id,oper_title,oper_status,oper_param,method,oper_type,log_info,oper_time FROM sys_oper_log WHERE  status='0'  " +
            "<if test=\"log.operName != null and log.operName != ''\">AND oper_name LIKE concat('%',#{log.operName},'%') </if> " +
            "<if test=\"log.operStatus != null and log.operStatus != ''\">AND oper_status=#{log.operStatus} </if> " +
            "<if test=\"log.maxOperTime != null\">AND oper_time &lt;= #{log.maxOperTime} </if> " +
            "<if test=\"log.minOperTime != null\">AND oper_time &gt;= #{log.minOperTime} </if> " +
            "order by create_time DESC" +
            "</script>")
    public IPage<LogListVO> selectLogPageList(Page page, @Param("log") LogListDTO logListDTO);

    /**
     * 清空日志
     */
    @Update("truncate table sys_oper_log")
    public void deleteAll();
}
