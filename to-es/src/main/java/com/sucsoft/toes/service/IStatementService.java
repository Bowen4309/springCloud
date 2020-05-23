package com.sucsoft.toes.service;

import com.sucsoft.toes.bean.SqlStatementToElkDO;

import java.util.List;

/**
 * <br>-lastModify:2019/8/25 10:37
 *
 * @author Lixiaoban
 * @version 1.0
 */
public interface IStatementService {

    /**
     * 查询全部，暂时没有条件
     * @return 全部规则
     */
    List<SqlStatementToElkDO> findAll();

    /**
     * 根据id查询单条记录
     * @param id id
     * @return 单条记录
     */
    SqlStatementToElkDO findOne(String id);

    /**
     * 关闭当前运行的当前规则
     * @param id id
     */
    void close(String id);

    /**
     * 开启当前运行的当前规则
     * @param id id
     */
    void open(String id);

    /**
     * 添加一条规则
     * @param elkDO 规则实体
     */
    void addOne(SqlStatementToElkDO elkDO);

    void deleteOne(String id);
}
