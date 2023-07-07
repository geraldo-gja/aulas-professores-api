package com.unitech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

@Configuration
public class SwaggerConfiguration {

	@Bean
    public Docket docket(){ 

        Docket d = new Docket(DocumentationType.SWAGGER_2).select()
                   .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();

        return d;
    }    
}
