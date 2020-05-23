package com.sucsoft.toes.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <br>-lastModify:2019/8/20 20:28
 *
 * @author Lixiaoban
 * @version 1.0
 */
public interface SqlMapper {

    /**
     * 直接调用sql的查询方法
     * @param sql sql
     * @return 返回通用查询结果
     */
    public List<Map> doSql(@Param("sql") String sql) throws Exception;

}
