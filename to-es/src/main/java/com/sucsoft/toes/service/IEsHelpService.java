package com.sucsoft.toes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sucsoft.toes.bean.EsSearchDO;
import com.sucsoft.toes.bean.Page;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <br>-lastModify:2019/8/21 10:00
 *
 * @author Lixiaoban
 * @version 1.0
 */
public interface IEsHelpService {

    /**
     * 创建一个elk索引，不传输任何数据仅仅建立一个空索引
     * @param indexName 索引名，如果索引名携带大写会自动转为小写
     * @return 是否成功创建，已经创建或者创建失败返回false
     * @throws IOException IO错误
     */
    boolean createIndex(String indexName) throws IOException;

    /**
     * 删除一个elk索引，删除索引中的数据
     * @param indexName 索引名，如果索引名携带大写会自动转为小写
     * @return 是否成功创建，已经创建或者创建失败返回false
     * @throws IOException IO错误
     */
    boolean removeIndex(String indexName) throws IOException;

    /**
     * 更新索引的mapping
     * @param indexMapping indexmapping
     * @param indexName 索引名，如果索引名携带大写会自动转为小写
     * @throws IOException IO错误
     */
    boolean updateIndexMapping(Map indexMapping, String indexName) throws IOException;

    /**
     * 根据索引获取mapping
     * @param indexName 索引名
     * @return mapping
     * @throws IOException IO错误
     */
    Map getIndexMapping(String indexName) throws IOException;

    /**
     * 根据传入的json串上传数据，非List
     * @param json json字符串
     * @param indexName 索引名，如果索引名携带大写会自动转为小写
     * @param idFieldName 主键id名称
     * @throws IOException IO错误
     */
    void saveItem(String json, String indexName, String idFieldName) throws IOException;

    /**
     * 根据传入的Map对象上传数据
     * @param data map对象
     * @param indexName 索引名，如果索引名携带大写会自动转为小写
     * @param idFieldName 主键id名称
     * @throws IOException IO错误
     */
    void saveItem(Map data, String indexName, String idFieldName) throws IOException;

    /**
     * 根据传入的数据库<b>pojo类</b>,类名小写作为indexName，pojo对应的数据表主键为id
     * 此处不考虑复合主键
     * @param obj pojo类
     * @throws IOException IO错误
     */
    void saveItem(Object obj) throws IOException;

    /**
     * 此处传入的<b>pojo类</b>,pojo对应的数据表主键为id
     * 此处不考虑复合主键
     * @param obj pojo类
     * @param indexName 索引名，自动转化小写
     * @throws IOException IO错误
     */
    void saveItem(Object obj, String indexName) throws IOException;


    /**
     * 查询==泛查询==不指定字段以及全索引查询
     * @param name 查询内容
     * @param pageSize 每页展示记录数
     * @param pageNo 页码
     * @return 查询结果
     * @throws IOException 查询时候格式错误
     */
    Page<Map> search(String name, Integer pageSize, Integer pageNo) throws IOException;

    /**
     * 根据索引名称指定字段名过滤数据后泛查询
     * @param indexName 索引名称
     * @param name 泛查询内容
     * @param pageSize 页号
     * @param pageNo 页码
     * @param key 过滤字段名
     * @param value 过滤字段值
     * @return 结果
     * @throws IOException 查询时格式错误
     */
    Page<Map> search(String indexName, String name, Integer pageSize, Integer pageNo, String[] key, String[] value) throws IOException;

    /**
     * 获取es的相对分词结果
     * @param name 查询词
     * @return 分词结果
     */
    List<String> analyze(String name) throws IOException;
}
