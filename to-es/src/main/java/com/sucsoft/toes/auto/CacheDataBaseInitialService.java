package com.sucsoft.toes.auto;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * <br>-lastModify:2019/8/25 8:53
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Configuration
public class CacheDataBaseInitialService {

    private JdbcTemplate template;

    public CacheDataBaseInitialService(@Qualifier("cacheJbdc") JdbcTemplate template){
        this.template = template;
    }

    /**
     * init db操作
     */
    @PostConstruct
    public void init(){
        String sql = "CREATE TABLE IF NOT EXISTS statementtoelk (" +
                "id varchar(35) primary key not null," +
                "statement varchar(1000) not null," +
                "cron varchar(15)," +
                "intervalSeconds int," +
                "indexName varchar(50) not null," +
                "idName varchar(30) not null," +
                "modifyColumn varchar(30)," +
                "createTime timestamp not null," +
                "status int not null," +
                "indexMapping varchar(4000))";
        template.execute(sql);
        String sql2 = "CREATE TABLE IF NOT EXISTS sqldescrption(" +
                "id varchar(100) primary key not null," +
                "columnName varchar(50) not null," +
                "columnDesc varchar(60) not null," +
                "indexName varchar(50) not null)";
        template.execute(sql2);
    }
}
