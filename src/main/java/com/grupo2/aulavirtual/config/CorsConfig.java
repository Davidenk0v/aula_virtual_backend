package com.grupo2.aulavirtual.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/users/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true)
                .maxAge(3600);

        registry.addMapping("/swagger-ui/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(false)
                .maxAge(3600);

        registry.addMapping("/auth/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(false)
                .maxAge(3600);

    }
}
