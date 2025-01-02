package com.example.g5be.service;

import com.example.g5be.model.Badge;
import com.example.g5be.repository.BadgeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final HttpSession httpSession;

    public BadgeService(BadgeRepository badgeRepository, HttpSession httpSession) {
        this.badgeRepository = badgeRepository;
        this.httpSession = httpSession;
    }

    public void registerBadge(Badge badge) {
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_ADMIN")) {
            throw new RuntimeException("Access Denied: Only admins can register badges.");
        }
        badgeRepository.save(badge);
    }

    public List<Badge> getBadgesByLecturer(String lecturerId) {
        String role = (String) httpSession.getAttribute("role");
        if (role == null || (!role.equals("ROLE_LECTURER") && !role.equals("ROLE_ADMIN"))) {
            throw new RuntimeException("Access Denied: Only Lecturers or admins can access badges.");
        }
        return badgeRepository.findBadgesByLecturer(lecturerId);
    }

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public void updateBadgeStatus(String bid, String status) {
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_ADMIN")) {
            throw new RuntimeException("Access Denied: Only admins can update badge status.");
        }

        Badge badge = badgeRepository.findById(bid);
        if (badge == null) {
            throw new RuntimeException("Badge not found.");
        }

        badge.setStatus(status); // Update the status of the badge
        badgeRepository.save(badge); // Save the updated badge
    }
}
