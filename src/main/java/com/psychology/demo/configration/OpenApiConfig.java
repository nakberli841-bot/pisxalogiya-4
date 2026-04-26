package com.psychology.demo.configration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Psychology Platform API")
                        .version("1.0")
                        .description("Psixoloji dəstək platforması üçün backend API sənədləşməsi")
                        .contact(new Contact().name("Nurlan").email("example@test.com")));
    }
}