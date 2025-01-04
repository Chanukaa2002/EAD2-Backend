package com.example.g5be.controller;

import com.example.g5be.model.CareerPortfolio;
import com.example.g5be.service.CareerPortfolioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            // Assign studentId from session to the portfolio object
            portfolio.setSid(studentId);

            // Update the portfolio for the logged-in student
            careerPortfolioService.updateCareerPortfolio(studentId, portfolio);
            return ResponseEntity.ok("Career portfolio updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/portfolio/{studentId}")
    public ResponseEntity<?> getPortfolioForStudent(@PathVariable String studentId) {
        // Validate Lecturer Role
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can view student portfolios.");
        }

        try {
            Map<String, Object> portfolioAndStudentDetails = careerPortfolioService.getPortfolioAndStudentByStudentId(studentId);
            return ResponseEntity.ok(portfolioAndStudentDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getportfolio")
    public ResponseEntity<?> getLoggedStudentPortfolio() {
        String role = (String) httpSession.getAttribute("role");
        String studentId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_STUDENT")) {
            return ResponseEntity.status(403).body("Access Denied: Only students can access their portfolio.");
        }

        try {
            Map<String, Object> portfolioAndStudentDetails = careerPortfolioService.getPortfolioAndStudentByStudentId(studentId);
            return ResponseEntity.ok(portfolioAndStudentDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
