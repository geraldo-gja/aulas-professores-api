package com.unitech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

/**
 * Classe de configuração do Swagger.
 * 
 * @author Geraldo Jorge
 * email: geraldo.gja@gmail.com
 * @version 1.0
 * Data: 06/07/2023
 */
@Configuration
public class SwaggerConfiguration {

	/**
	 * Gera automaticamente a documentação Rest APi.
	 * 
	 * @return Docket
	 */
	@Bean
    public Docket docket(){ 

        Docket d = new Docket(DocumentationType.SWAGGER_2).select()
                   .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();

        return d;
    }    
}
