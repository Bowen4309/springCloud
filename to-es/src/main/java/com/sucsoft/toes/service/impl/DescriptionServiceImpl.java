package com.sucsoft.toes.service.impl;

import com.sucsoft.toes.bean.ColumnDescVO;
import com.sucsoft.toes.bean.SimpleColumnDescVO;
import com.sucsoft.toes.dao.SqlDescriptionMapper;
import com.sucsoft.toes.service.IDescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Libin
 */
@Service
public class DescriptionServiceImpl implements IDescriptionService {


    private SqlDescriptionMapper sqlDescriptionMapper;

    public DescriptionServiceImpl(SqlDescriptionMapper sqlDescriptionMapper){
        this.sqlDescriptionMapper = sqlDescriptionMapper;
    }

    @Override
    public List<ColumnDescVO> findByIndexName(String indexName) {
        return sqlDescriptionMapper.findByIndexName(indexName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDescToIndexName(String indexName, List<SimpleColumnDescVO> data) {
        if(null == data || data.isEmpty() || StringUtils.isEmpty(indexName)){
            throw new IllegalArgumentException("data参数和indexName不能为空");
        }
        removeOneIndexDesc(indexName);
        for (SimpleColumnDescVO vo : data) {
            sqlDescriptionMapper.save(new ColumnDescVO()
                    .setColumnDesc(vo.getColumnDesc())
                    .setColumnName(vo.getColumnName())
                    .setId(indexName + vo.getColumnName())
                    .setIndexName(indexName));
        }
    }

    @Override
    public void removeOneIndexDesc(String indexName) {
        if(StringUtils.isEmpty(indexName)){
            throw new IllegalArgumentException("indexName不可以为空");
        }
        sqlDescriptionMapper.removeByIndexName(indexName);
    }

    @Override
    public void updateOneRecord(String indexName, String columnName, String updateDesc) {
        if(StringUtils.isEmpty(indexName) || StringUtils.isEmpty(columnName)){
            throw new IllegalArgumentException("indexName和columnName不能为空");
        }
        sqlDescriptionMapper.updateDesc(indexName + columnName, updateDesc);
    }

    @Override
    public void addOneRecord(String indexName, String columnName, String addDesc) {
        if(StringUtils.isEmpty(indexName) || StringUtils.isEmpty(columnName)){
            throw new IllegalArgumentException("indexName和columnName不能为空");
        }
        sqlDescriptionMapper.save(new ColumnDescVO()
                .setIndexName(indexName)
                .setId(indexName + columnName)
                .setColumnName(columnName)
                .setColumnDesc(addDesc));
    }

    @Override
    public void removeOneRecord(String indexName, String columnName) {
        if(StringUtils.isEmpty(indexName) || StringUtils.isEmpty(columnName)){
            throw new IllegalArgumentException("indexName和columnName不能为空");
        }
        sqlDescriptionMapper.remove(indexName + columnName);
    }
}
