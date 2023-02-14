package com.service.file.dofile.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: 公共服务
 * @Package: com.service.file.dofile.config
 * @ClassName: SwaggerConfig
 * @Author: yunabo
 * @Description:
 * @Date: 2020/4/16 0016 15:25
 * @Version: 1.0
 */

@EnableSwagger2
@EnableSwaggerBootstrapUI
@Configuration
public class SwaggerConfig {
   /* @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
    }*/
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.service.file.dofile.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("数据引导工具")
                .contact(new Contact("Lisnasi", "http://47.99.210.191", "1047573643@qq.com"))
                .version("1.0")
                .build();
    }

}
