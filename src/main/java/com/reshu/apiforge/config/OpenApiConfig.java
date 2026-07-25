package com.reshu.apiforge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiForgeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("APIForge")
                        .version("1.0")
                        .description("Secure REST API Management Platform"));
    }
}