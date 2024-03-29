package com.Bibo;

import com.Bibo.common.config.swagger.EnableSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableSwagger
@MapperScan({"com.Bibo.business.dao","com.Bibo.business.mapper"})
@ComponentScan(value = {"com.Bibo.common.config.redisConfig","com.Bibo.common.util","com.Bibo.business"})
public class BusinessApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(BusinessApplication.class, args);
    }
}
