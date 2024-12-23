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
                        "/api/v1/admin/login", // Admin login
                        "/api/v1/lecturer/login", // Lecturer login
                        "/api/v1/student/login", // Student login
                        "/api/v1/admin/lecturer/", // Lecturer registration endpoint
                        "/api/v1/admin/lecturer/**", // Lecturer update endpoint
                        "/api/v1/admin/lecturer/remove/**", // Lecturer delete endpoint
                        "/api/session/details", // Session details
                        "/api/v1/admin/batches", // Badge registration
                        "/api/badge/lecturer/badges",/// ////////
                        "/lecturer/events/create/workshop",/// /////////
                        "/lecturer/events/workshops",/// ///////
                        "/api/v1/admin/student/register", // Student registration
                        "/api/v1/admin/student/**", // Student update endpoint
                        "/api/v1/admin/student/remove/", // Student delete endpoint
                        "/api/v1/lecturer/events/create/interview",// Create interview
                        "/api/v1/lecturer/events/create/workshop",   // Create workshop
                        "/api/events/list",            // List all events
                        "/api/events/{eid}/update-status", // Update event status
                        "/api/events/{eid}",           // Delete event
                        "/api/events/details/**",     // View event details by ID
                        "lecturer/events/create/announcement",
                        "lecturer/events/announcements",
                        "/api/student/batch/**",
                        "lecturer/events/create/interview",
                        "lecturer/events/lecturer/interviews",
                        "admins/state",
                        "admins/events",
                        "lecturer/feedback/send",
                        "lecturer/assign/interviews"
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
