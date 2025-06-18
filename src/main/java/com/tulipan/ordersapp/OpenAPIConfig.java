package com.tulipan.ordersapp;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Orders App API")
                .version("1.0.0")
                .description("API documentation for the Orders App")
                .contact(new Contact()
                    .name("Support Team")
                    .email("car.ruiz90@gmail.com")));
    }
}
