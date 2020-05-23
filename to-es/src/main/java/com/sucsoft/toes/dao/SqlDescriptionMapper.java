package com.sucsoft.toes.dao;

import com.sucsoft.toes.bean.ColumnDescVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Libin
 */
public interface SqlDescriptionMapper{
    /**
     * 保存方法
     * @param vo 需要保存的对象
     * @return 返回修改记录
     */
    int save(ColumnDescVO vo);

    /**
     * 根据索引名称查询注释
     * @param indexName 索引名
     * @return 注释
     */
    List<ColumnDescVO> findByIndexName(@Param("indexName") String indexName);

    /**
     * 获取单挑记录
     * @param id id
     * @return 单挑记录
     */
    ColumnDescVO getOne(@Param("id") String id);

    /**
     * 删除单条记录
     * @param id id
     * @return 影响行数
     */
    int remove(@Param("id") String id);

    /**
     * 根据indexName删除这个索引下面所有的注释记录
     * @param indexName 索引名称
     * @return 影响行数
     */
    int removeByIndexName(@Param("indexName") String indexName);

    /**
     * 根据id修改注释
     * @param id id
     * @param newDesc 新注释
     * @return 更改行数
     */
    int updateDesc(String id, String newDesc);
}
