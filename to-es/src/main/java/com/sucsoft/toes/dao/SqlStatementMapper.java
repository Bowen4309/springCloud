package com.sucsoft.toes.dao;

import com.sucsoft.toes.bean.SqlStatementToElkDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>-lastModify:2019/8/25 10:23
 *
 * @author Lixiaoban
 * @version 1.0
 */
public interface SqlStatementMapper {

    List<SqlStatementToElkDO> list();

    int save(SqlStatementToElkDO elkDO);

    SqlStatementToElkDO findOne(@Param("id") String id);

    int updateStatus(SqlStatementToElkDO elkDO);

    int delete(@Param("id") String id);
}
