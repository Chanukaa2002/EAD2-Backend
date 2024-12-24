package com.example.g5be.controller;

import com.example.g5be.dto.ApplicationState;
import com.example.g5be.model.Event;
import com.example.g5be.service.AdminService;
import com.example.g5be.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminService adminService;
    private final EventService eventService;
    private final HttpSession httpSession;

    public AdminController(AdminService adminService, EventService eventService, HttpSession httpSession) {
        this.adminService = adminService;
        this.eventService = eventService;
        this.httpSession = httpSession;
    }

    @GetMapping("/state")
    public ResponseEntity<?> getApplicationState() {
        // Check if the user is an admin
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Access Denied: Only admins can view application state.");
        }

        try {
            ApplicationState state = adminService.getApplicationState();
            return ResponseEntity.ok(state);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/events")
    public ResponseEntity<?> viewEvents(@RequestParam(required = false) String status) {
        // Validate admin role
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Access Denied: Only admins can view events.");
        }

        try {
            List<Event> events;
            if (status == null) {
                events = eventService.findAllEvents();
            } else if (status.equalsIgnoreCase("complete")) {
                events = eventService.findCompletedEvents();
            } else if (status.equalsIgnoreCase("upcoming")) {
                events = eventService.findUpcomingEvents();
            } else {
                return ResponseEntity.badRequest().body("Invalid status value. Use 'complete' or 'upcoming'.");
            }

            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
