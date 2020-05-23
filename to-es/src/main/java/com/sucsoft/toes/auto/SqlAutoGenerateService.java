package com.sucsoft.toes.auto;

import com.sucsoft.toes.bean.SqlStatementToElkDO;
import com.sucsoft.toes.mapper.SqlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <br>-lastModify:2019/8/24 11:44
 *
 * @author Lixiaoban
 * @version 1.0
 */
@ConditionalOnProperty(name = "sql.autoGenerator",havingValue = "true")
@Configuration
public class SqlAutoGenerateService {

    private AutoElkService service;

    private SqlMapper sqlMapper;

    @Value("${sql.initSql}")
    private String initSql;

    @Value("${sql.selectPkSql}")
    private String selectPkSql;

    @Value("${sql.selectDesSql}")
    private String selctDesSql;

    public SqlAutoGenerateService(AutoElkService service, SqlMapper mapper){
        this.sqlMapper = mapper;
        this.service = service;
    }

    @PostConstruct
    public void init() throws Exception{
        List<Map> test = sqlMapper.doSql(initSql);
        for (Map map : test) {
            //主键名
            String pk = sqlMapper.doSql(String.format(selectPkSql, map.get("tablename").toString())).get(0).get("colname").toString();
            //注释
            List<Map> tabledesc = sqlMapper.doSql(String.format(selctDesSql, map.get("tablename").toString()));
            service.addColumnDesc(tabledesc,map.get("tablename").toString().toLowerCase());
            service.addSqlStatement(new SqlStatementToElkDO()
                    .setCreateTime(new Date())
                    .setCron("")
                    //24 * 60 * 60L
                    .setIntervalSeconds(24 * 60 * 60L)
                    //主键
                    .setIdName(pk)
                    .setStatus(SqlStatementToElkDO.STATUS_OPEN)
                    .setId(map.get("tablename").toString())
                    .setModifyColumn("")
                    .setIndexName(map.get("tablename").toString().toLowerCase())
                    .setStatement(String.format("select * from %s",map.get("tablename").toString()))
            );
        }
    }
}
