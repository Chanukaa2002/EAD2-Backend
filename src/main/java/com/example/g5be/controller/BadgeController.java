package com.example.g5be.controller;

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
            // Fetch badges for the lecturer
            List<Badge> badges = badgeService.getBadgesByLecturer(lecturerId);
            return ResponseEntity.ok(badges);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/admin/batches/all")
    public ResponseEntity<?> getAllBadges() {
        String role = (String) httpSession.getAttribute("role");

        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Access Denied: Only admins can view all badges.");
        }

        try {
            List<Badge> badges = badgeService.getAllBadges();
            return ResponseEntity.ok(badges);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
