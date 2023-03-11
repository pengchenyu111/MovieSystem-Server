package com.pcy.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestController;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * @Author PengChenyu
 * @Date 2023/3/6 23:56
 * @Version 1.0
 */

@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@EnableKnife4j
@Configuration
public class Swagger3Config {

    @Value("${spring.application.name}")
    private String PROJECT_NAME;

    /**
     * 配置基本信息
     *
     * @return
     */
    @Bean(value = "uaaApi")
    @Order(value = 1)
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(PROJECT_NAME)
                .description("电影推荐系统-用户登录接口文档")
                .termsOfServiceUrl("http://localhost")
                .contact(new Contact("PengChenyu", "http://localhost", "iampengchenyu@163.com"))
                .version("1.0")
                .build();
    }


    /**
     * 配置文档生成最佳实践
     *
     * @param apiInfo
     * @return
     */
    @Bean
    public Docket createRestApi(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pcy"))
                .build();
    }



}
