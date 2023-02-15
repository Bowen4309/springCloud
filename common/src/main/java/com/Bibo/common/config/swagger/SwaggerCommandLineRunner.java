package com.Bibo.common.config.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
@Component
public class SwaggerCommandLineRunner implements CommandLineRunner {

    @Value("${server.port:8080}")
    private String serverPort;



    @Override
    public void run(String... args) {
        log.info("swagger url:http://localhost:" + serverPort + "/doc.html");
    }
}
