package com.sucsoft.toes.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 来源数据库配置
 * <br>-lastModify:2019/8/25 9:18
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Configuration
@MapperScan(basePackages = SourceDataConfiguration.PACKAGE, sqlSessionFactoryRef = "sourceSqlSessionFactory")
public class SourceDataConfiguration {

    static final String PACKAGE = "com.sucsoft.toes.mapper";

    private static final String MAPPER_LOCATION = "classpath:/mapper/*.xml";

    @Value("${spring.datasource.source.username}")
    private String username;

    @Value("${spring.datasource.source.password}")
    private String password;

    @Value("${spring.datasource.source.url}")
    private String url;

    @Value("${spring.datasource.source.driver-class-name}")
    private String driverClassName;

    @Bean("source")
    @Primary
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(name = "sourceTransactionManager")
    @Primary
    public DataSourceTransactionManager cacheTransactionManager(@Qualifier("source") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sourceSqlSessionFactory")
    @Primary
    public SqlSessionFactory cacheSqlSessionFactory(@Qualifier("source") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true);
        sessionFactory.setConfiguration(configuration);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SourceDataConfiguration.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
