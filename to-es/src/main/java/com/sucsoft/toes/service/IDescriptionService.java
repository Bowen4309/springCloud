package com.sucsoft.toes.service;

import com.sucsoft.toes.bean.ColumnDescVO;
import com.sucsoft.toes.bean.SimpleColumnDescVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Libin
 */
public interface IDescriptionService {

    /**
     * 根据indexName查询其所有的注释信息
     * @param indexName 索引名称
     * @return 所有注释信息
     */
    List<ColumnDescVO> findByIndexName(String indexName);

    /**
     * 根据indexName添加或更新所有注释信息(此操作会重置之前存储的信息)
     * @param indexName 索引名称
     * @param data 注释信息
     */
    void addDescToIndexName(String indexName, List<SimpleColumnDescVO> data);

    /**
     * 根据indexName删除这个indexName下的所有注释信息
     * @param indexName 索引名称
     */
    void removeOneIndexDesc(String indexName);

    /**
     * 更新单个注释信息
     * @param indexName 索引名称
     * @param columnName 字段名称
     * @param updateDesc 更新的注释
     */
    void updateOneRecord(String indexName,String columnName, String updateDesc);

    /**
     * 添加单个注释信息
     * @param indexName 索引名称
     * @param columnName 字段名称
     * @param addDesc 新增的注释
     */
    void addOneRecord(String indexName, String columnName, String addDesc);

    /**
     * 删除单个注释信息
     * @param indexName 索引名称
     * @param columnName 字段名称
     */
    void removeOneRecord(String indexName, String columnName);
}
