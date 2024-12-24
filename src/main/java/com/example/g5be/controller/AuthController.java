package com.example.g5be.controller;

import com.example.g5be.model.LoginRequest;
import com.example.g5be.model.LoginResponse;
import com.example.g5be.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody LoginRequest request) {
        try {
            String token = authService.authenticateAdmin(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse("Admin login successful", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(new LoginResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/lecturer/login")
    public ResponseEntity<LoginResponse> lecturerLogin(@RequestBody LoginRequest request) {
        try {
            String token = authService.authenticateLecturer(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse("Lecturer login successful", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(new LoginResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/student/login")
    public ResponseEntity<LoginResponse> studentLogin(@RequestBody LoginRequest request) {
        try {
            String token = authService.authenticateStudent(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse("Student login successful", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(new LoginResponse(e.getMessage(), null));
        }
    }
}
