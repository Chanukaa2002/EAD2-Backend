package com.example.g5be.service;


import com.example.g5be.model.Badge;
import com.example.g5be.repository.BadgeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final HttpSession httpSession;

    public BadgeService(BadgeRepository badgeRepository, HttpSession httpSession) {
        this.badgeRepository = badgeRepository;
        this.httpSession = httpSession;
    }

    public void registerBadge(Badge badge) {
        // Check if the session role is admin
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_ADMIN")) {
            throw new RuntimeException("Access Denied: Only admins can register badges.");
        }

        // Save the badge
        badgeRepository.save(badge);
    }
}
