package com.Bibo.plug;

import com.Bibo.common.config.swagger.EnableSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableSwagger
@MapperScan({"com.xdh.traffic_plug.model.dao","com.xdh.traffic_plug.*.mapper"})
@ComponentScan(value = {"com.Bibo.common.config.redisConfig", "com.Bibo.traffic_common.util", "com.Bibo.plug"})
@EnableAsync
public class PlugApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlugApplication.class, args);
    }

}
