package com.project.finances.infra.config;

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


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(
                DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.project.finances.app.presentation.rest"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Finances Spring Boot")
                .description("This API it's used to available interface access to manager flow cash")
                .version("Version 0.0.3")
                .contact(new Contact("Joao Flavio","https://github.com/flaviosenne", "facildespesa@gmail.com"))
                .license("copy right")
                .build();

    }

}
