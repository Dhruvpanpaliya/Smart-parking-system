package com.parking.smartparking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI smartParkingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart Parking Management System API")
                        .version("1.0")
                        .description("Complete REST API documentation for the Smart Parking Management System"));
    }
}
