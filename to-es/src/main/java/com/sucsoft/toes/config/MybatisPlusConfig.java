package com.sucsoft.toes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * <br>-lastModify:2019/8/8 13:44
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Configuration
public class MybatisPlusConfig {

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
}
