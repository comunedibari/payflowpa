package it.regioneveneto.mygov.payment.mypivotsb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${info.app.version}")
	private String appVersion;
	
	@Value("${info.app.name}")
	private String appName;
	
	@Value("${info.app.description}")
	private String appDescr;
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(new ApiInfoBuilder().version(appVersion).title(appName).description(appDescr).build());
    }
}