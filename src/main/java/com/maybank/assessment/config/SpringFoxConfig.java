package com.maybank.assessment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableWebMvc
@Component
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.maybank.assessment.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiData() {
        return new ApiInfo(
                "Client API",
                "All the API's of Client",
                "1.0",
                "Open Source",
                new springfox.documentation.service.Contact("Azli", "http://www.azlicn.com", "azlicn@gmail.com"),
                "API Licence",
                "https://www.azlicn.com",
                Collections.EMPTY_LIST);
    }
}
