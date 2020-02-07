package com.embl.backend.config;

import com.embl.backend.util.constants.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@EnableSwagger2
@Configuration
public class SwaggerConfig {


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(AppConstants.SWAGGER_BASE_PACKAGE)).build().securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo getApiInfo() {
        Contact contact =  new Contact("Harsh Vardhan", "https://gitlab.com/forharsh", "infoharsh87@gmail.com");
        return new ApiInfoBuilder()
                    .title(AppConstants.SWAGGER_API_TITLE)
                    .description(AppConstants.SWAGGER_API_DESCRIPTION)
                    .version(AppConstants.SWAGGER_VERSION)
                    .contact(contact)
                    .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(AppConstants.API_KEY_NAME, AppConstants.AUTHORIZATION_HEADER, AppConstants.HEADER);
    }

}
