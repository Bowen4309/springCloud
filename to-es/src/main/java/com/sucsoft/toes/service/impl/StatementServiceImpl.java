package com.sucsoft.toes.service.impl;

import com.sucsoft.toes.auto.AutoElkService;
import com.sucsoft.toes.bean.SqlStatementToElkDO;
import com.sucsoft.toes.dao.SqlStatementMapper;
import com.sucsoft.toes.service.IStatementService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <br>-lastModify:2019/8/25 10:40
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Service
public class StatementServiceImpl implements IStatementService {

    private SqlStatementMapper mapper;

    private AutoElkService autoElkService;

    public StatementServiceImpl(SqlStatementMapper mapper,AutoElkService elkService){
        this.mapper = mapper;
        this.autoElkService = elkService;
    }

    @Override
    public List<SqlStatementToElkDO> findAll() {
        List<SqlStatementToElkDO> sqlStatementToElkDOS = mapper.list();
        return sqlStatementToElkDOS;
    }

    @Override
    public SqlStatementToElkDO findOne(String id) {
        SqlStatementToElkDO sqlStatementToElkDO = mapper.findOne(id);
        return sqlStatementToElkDO;
    }

    @Override
    public void close(String id) {
        SqlStatementToElkDO sqlStatementToElkDO = mapper.findOne(id);
        autoElkService.modifyStatus(sqlStatementToElkDO, SqlStatementToElkDO.STATUS_CLOSE);
    }

    @Override
    public void open(String id) {
        SqlStatementToElkDO sqlStatementToElkDO = mapper.findOne(id);
        autoElkService.modifyStatus(sqlStatementToElkDO, SqlStatementToElkDO.STATUS_OPEN);
    }

    @Override
    public void addOne(SqlStatementToElkDO elkDO) {
        elkDO.setStatus(SqlStatementToElkDO.STATUS_OPEN);
        elkDO.setId(elkDO.getIndexName());
        elkDO.setCreateTime(new Date());
        autoElkService.addSqlStatement(elkDO);
    }

    @Override
    public void deleteOne(String id) {
        autoElkService.deleteSqlStatement(id);
    }
}
