package com.example.g5be.controller;

import com.example.g5be.dto.AnnouncementDTO;
import com.example.g5be.dto.AnnouncementRequest;
import com.example.g5be.model.Announcement;
import com.example.g5be.model.Event;
import com.example.g5be.service.AnnouncementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final HttpSession httpSession;

    public AnnouncementController(AnnouncementService announcementService, HttpSession httpSession) {
        this.announcementService = announcementService;
        this.httpSession = httpSession;
    }

    @PostMapping("/lecturer/announcements")
    public ResponseEntity<String> createAnnouncement(@RequestBody AnnouncementRequest request) {
        // Validate Lecturer Role
        String role = (String) httpSession.getAttribute("role");
        String lecturerId = (String) httpSession.getAttribute("id");
        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can create announcements.");
        }

        try {
            // Create Event
            Event event = new Event();
            event.setEid(request.getEid());
            event.setDate(request.getDate());
            event.setName(request.getName());
            event.setStatus(request.getStatus());
            event.setLid(lecturerId);

            // Create Announcement
            Announcement announcement = new Announcement();
            announcement.setDescription(request.getDescription());
            announcement.setBid(request.getBid());

            // Save both Event and Announcement
            announcementService.createAnnouncementWithEvent(event, announcement);

            return ResponseEntity.ok("Announcement and associated Event created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/get/announcements")
    public ResponseEntity<?> getAnnouncementsForLecturer() {
        // Check if the user is a lecturer
        String role = (String) httpSession.getAttribute("role");
        String lecturerId = (String) httpSession.getAttribute("id"); // Get LID from session

        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can view announcements.");
        }

        try {

            List<Announcement> announcements = announcementService.getAnnouncementsByLecturerId(lecturerId);
            return ResponseEntity.ok(announcements);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/students/announcements") //change here
    public ResponseEntity<?> getAnnouncementsForStudent() {
        // Validate Student Role
        String role = (String) httpSession.getAttribute("role");
        String studentId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_STUDENT")) {
            return ResponseEntity.status(403).body("Access Denied: Only students can view announcements.");
        }

        try {
            // Fetch announcements for the logged-in student
            List<AnnouncementDTO> announcements = announcementService.getAnnouncementsForStudent(studentId);
            return ResponseEntity.ok(announcements);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
