package com.sucsoft.toes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
/*import oracle.sql.TIMESTAMP;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;

/**
 * <br>-lastModify:2019/8/8 13:44
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Configuration
public class HelpConfig {

    /**
     * SQL执行效率插件
     *  设置 dev test 环境开启
     * @return 1
     */
//    @Bean
////    @Profile({"dev","test"})
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }
//
//    /**
//     * 分页插件
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
//        return paginationInterceptor;
//    }

   /* @Autowired
    public void objectMapper(ObjectMapper objectMapper, OracleTimestampSerializer serializer){
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(TIMESTAMP.class,serializer);
        objectMapper.registerModule(simpleModule);
    }*/
}
