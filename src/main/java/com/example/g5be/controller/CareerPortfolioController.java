package com.example.g5be.controller;

import com.example.g5be.model.CareerPortfolio;
import com.example.g5be.service.CareerPortfolioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class CareerPortfolioController {

    private final CareerPortfolioService careerPortfolioService;
    private final HttpSession httpSession;

    public CareerPortfolioController(CareerPortfolioService careerPortfolioService, HttpSession httpSession) {
        this.careerPortfolioService = careerPortfolioService;
        this.httpSession = httpSession;
    }

    @PutMapping("/portfolio")
    public ResponseEntity<String> updatePortfolio(@RequestBody CareerPortfolio portfolio) {
        // Get student ID from session
        String role = (String) httpSession.getAttribute("role");
        String studentId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_STUDENT")) {
            return ResponseEntity.status(403).body("Access Denied: Only students can update their portfolio.");
        }

        try {
            // Update the portfolio for the logged-in student
            careerPortfolioService.updateCareerPortfolio(studentId, portfolio);
            return ResponseEntity.ok("Career portfolio updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
