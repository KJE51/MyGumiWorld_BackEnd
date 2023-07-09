package com.mygumi.insider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

//swagger 사용을 위해 선언한다.
@EnableSwagger2
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "구미 인사이더 - board backend",
                "구미 인사이더의 board 파트의 백엔드 swagger",
                "V1",
                "Terms of service",
                new Contact("administrator", "www.example.com", "administrator@email.com"),
                "License of API", "www.example.com",
                Collections.emptyList());
    }

//    @Bean
//    public Docket userApi() {
//        return getDocket("회원", Predicates.or(PathSelectors.regex("/oauth.*")));
//    }
//
//    public Docket getDocket(String groupName, Predicate<String> predicate) {
//        return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).apiInfo(apiInfo()).select()
//                .apis(RequestHandlerSelectors.basePackage("com.mygumi.insider.controller")).paths(predicate)
//                .apis(RequestHandlerSelectors.any()).build();
//    }

    // swagger ui 설정.
    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder().displayRequestDuration(true).validatorUrl("").build();
    }
}