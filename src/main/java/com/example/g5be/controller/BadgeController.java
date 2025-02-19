package com.example.g5be.controller;

import com.example.g5be.dto.BadgeSummaryDTO;
import com.example.g5be.model.Badge;
import com.example.g5be.service.BadgeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BadgeController {

    private final BadgeService badgeService;
    private final HttpSession httpSession;

    public BadgeController(BadgeService badgeService, HttpSession httpSession) {
        this.badgeService = badgeService;
        this.httpSession = httpSession;
    }

    @PostMapping("/admin/batches")
    public ResponseEntity<String> registerBadge(@RequestBody Badge badge) {
        badgeService.registerBadge(badge);
        return ResponseEntity.ok("Badge registered successfully");
    }

    @GetMapping("/lecturer/badges")
    public ResponseEntity<?> getBadgesByLecturer() {
        // Get lecturer ID from session
        String role = (String) httpSession.getAttribute("role");
        String lecturerId = (String) httpSession.getAttribute("id");

        // Ensure the user is a lecturer
        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can view their badges.");
        }

        try {
            // Fetch badge summaries for the lecturer
            List<BadgeSummaryDTO> badges = badgeService.getBadgeSummariesByLecturer(lecturerId);
            return ResponseEntity.ok(badges);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/admin/batches/all")
    public ResponseEntity<?> getAllBadges() {
        String role = (String) httpSession.getAttribute("role");

        // Ensure the user is an admin
        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Access Denied: Only admins can view all badges.");
        }

        try {
            // Fetch all badge summaries
            List<BadgeSummaryDTO> badges = badgeService.getBadgeSummaries();
            return ResponseEntity.ok(badges);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/admin/batches/{bid}")
    public ResponseEntity<String> updateBadgeStatus(@PathVariable("bid") String bid, @RequestParam("status") String status) {
        String role = (String) httpSession.getAttribute("role");

        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Access Denied: Only admins can update badge status.");
        }

        try {
            badgeService.updateBadgeStatus(bid, status);
            return ResponseEntity.ok("Badge status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
