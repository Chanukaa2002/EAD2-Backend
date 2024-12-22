package com.example.g5be.controller;


import com.example.g5be.model.Badge;
import com.example.g5be.service.BadgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }


    @PostMapping("/batches")
    public ResponseEntity<String> registerBadge(@RequestBody Badge badge) {
        badgeService.registerBadge(badge);
        return ResponseEntity.ok("Badge registered successfully");
    }
}
