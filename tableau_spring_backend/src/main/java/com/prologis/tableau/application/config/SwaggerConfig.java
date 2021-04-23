package com.prologis.tableau.application.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * This class contains the Swagger configuration details
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.prologis.tableau.application")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo()).tags(new Tag("Middleware", "API's to handle details and preferences of tableau users"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API", "Prologis Tableau Middleware", "", "", null, "", "",
				Collections.emptyList());
	}
}
