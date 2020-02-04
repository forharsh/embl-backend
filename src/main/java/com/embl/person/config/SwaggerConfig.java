package com.embl.person.config;

import com.embl.person.util.constants.Constants;
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

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(Constants.SWAGGER_BASE_PACKAGE)).build().securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo getApiInfo() {
        Contact contact =  new Contact("Harsh Vardhan", "https://gitlab.com/forharsh", "infoharsh87@gmail.com");
        return new ApiInfoBuilder()
                    .title("REST APIs related to Person Entity!!!!")
                    .description("Swagger ui lists all apis related to Person Entity")
                    .version("0.0.1-SNAPSHOT")
                    .contact(contact)
                    .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

}
