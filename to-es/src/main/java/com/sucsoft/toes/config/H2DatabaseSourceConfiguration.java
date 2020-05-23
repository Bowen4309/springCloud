package com.sucsoft.toes.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * h2 database配置类
 * <br>-lastModify:2019/8/25 8:37
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = H2DatabaseSourceConfiguration.PACKAGE, sqlSessionFactoryRef = "cacheSqlSessionFactory")
public class H2DatabaseSourceConfiguration {


    //dao层的包路径
    static final String PACKAGE = "com.sucsoft.toes.dao";
    //mapper文件的相对路径
    private static final String MAPPER_LOCATION = "classpath:dao/*.xml";

    @Value("${spring.datasource.cache.username}")
    private String username;

    @Value("${spring.datasource.cache.password}")
    private String password;

    @Value("${spring.datasource.cache.url}")
    private String url;

    @Value("${spring.datasource.cache.driver-class-name}")
    private String driverClassName;


    @Bean("cacheDataSource")
    public DataSource h2DataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean("cacheJbdc")
    public JdbcTemplate jdbcTemplate(@Qualifier("cacheDataSource") DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }


    @Bean(name = "cacheTransactionManager")
    public DataSourceTransactionManager cacheTransactionManager(@Qualifier("cacheDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "cacheSqlSessionFactory")
    public SqlSessionFactory cacheSqlSessionFactory(@Qualifier("cacheDataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(H2DatabaseSourceConfiguration.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
