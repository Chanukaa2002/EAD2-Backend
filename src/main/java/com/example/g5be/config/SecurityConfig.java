package com.example.g5be.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for APIs
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/auth/admin/login", // Admin login
                        "/api/auth/lecturer/login", // Lecturer login
                        "/api/auth/student/login", // Student login
                        "/api/lecturer/register", // Lecturer registration endpoint
                        "/api/lecturer/update/**", // Lecturer update endpoint
                        "/api/lecturer/delete/**", // Lecturer delete endpoint
                        "/api/session/details", // Session details
                        "/api/badge/register", // Badge registration
                        "/api/student/register", // Student registration
                        "/api/student/update/**", // Student update endpoint
                        "/api/student/delete/**"  // Student delete endpoint
                ).permitAll() // Allow unauthenticated access to these endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS); // Use session management
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
