package com.rakutten.utils.context.springfox;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public abstract class SpringfoxConfigurer {

    @Bean
    public Docket springfoxApiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage(basePackage()))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(this.apiInfo());
    }

    protected abstract ApiInfo apiInfo();
    
    protected abstract String basePackage();
    
}
