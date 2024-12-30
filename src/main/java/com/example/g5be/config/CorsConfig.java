package com.example.g5be.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Allows any origin pattern
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true) // Enable cookies
                .allowedHeaders("*");
    }
}
