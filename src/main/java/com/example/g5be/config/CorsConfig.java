package com.example.g5be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*") // Allow all ports on localhost
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true) // Enable cookies/credentials
                .allowedHeaders("*");
    }
}
