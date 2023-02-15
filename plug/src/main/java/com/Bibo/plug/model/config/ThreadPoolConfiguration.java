package com.Bibo.plug.model.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class ThreadPoolConfiguration {

    @Bean(name = "defaultThreadPoolExecutor",destroyMethod = "shutdown")
    public ThreadPoolExecutor systemCheckPoolExecutorSrvice(){

        return new ThreadPoolExecutor(3,10,60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(1000),
                new ThreadFactoryBuilder().setNameFormat("ddefault-executor-%d").build(),
                (r,executor) -> log.error("system pool is full!"));
    }
}
