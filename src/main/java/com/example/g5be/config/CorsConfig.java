package com.example.g5be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "http://13.51.233.133:8080", // Allow backend public IP URL
                        "http://nibmevex.edu.lk",     // Allow frontend URL
                        "http://localhost:*"         // For local development
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true) // Allow cookies/credentials
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookie");
    }
}
