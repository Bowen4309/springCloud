package com.Bibo.common.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement
@Configuration
//@MapperScan("com.xdh.*.mapper.*")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor =new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.POSTGRE_SQL);
        paginationInnerInterceptor.setOverflow(true);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return mybatisPlusInterceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
    //分页插件
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
//    return interceptor;
//}
//    //Sequence主键
//    @Bean
//    public IKeyGenerator keyGenerator() {
//        return new H2KeyGenerator();
//    }
//
//    @Bean
//    public ConfigurationCustomizer mybatisConfigurationCustomizer(){
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(MybatisConfiguration configuration) {
//                configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
//            }
//        };
//    }
}
