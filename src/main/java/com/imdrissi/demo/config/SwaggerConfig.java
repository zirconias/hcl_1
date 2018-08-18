package com.imdrissi.demo.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
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

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@Slf4j
@EnableSwagger2
public class SwaggerConfig {

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("HCL SEARCH API")
        .description("API FOR HCL.")
        .termsOfServiceUrl("terms of services")
        .contact(new Contact("islam drissi", "https://imdrissi.com", "islam.drissi@gmail.com"))
        .license("MIT")
        .version("0.1")
        .build();
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("ace")
        .apiInfo(apiInfo())
        .select()
        .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
        .paths(apiPaths())
        .paths(PathSelectors.any())
        .build();
  }

  private Predicate<String> apiPaths() {
    return or(
        regex("/api/albums.*"), regex("/api/books.*"), regex("/api/search.*"), regex("/auth.*"));
  }
}
