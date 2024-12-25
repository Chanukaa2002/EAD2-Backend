package com.example.g5be.controller;

import com.example.g5be.dto.FeedbackRequest;
import com.example.g5be.model.Feedback;
import com.example.g5be.service.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final HttpSession httpSession;

    public FeedbackController(FeedbackService feedbackService, HttpSession httpSession) {
        this.feedbackService = feedbackService;
        this.httpSession = httpSession;
    }

    @PostMapping("/lecturer/send")
    public ResponseEntity<String> sendFeedbackToStudent(@RequestBody FeedbackRequest request) {
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can send feedback.");
        }

        try {
            feedbackService.sendFeedbackToStudent(request);
            return ResponseEntity.ok("Feedback sent to student successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/student/send")
    public ResponseEntity<String> sendFeedbackToLecturer(@RequestBody FeedbackRequest request) {
        String role = (String) httpSession.getAttribute("role");
        String studentId = (String) httpSession.getAttribute("id"); // Get student ID from session

        if (role == null || !role.equals("ROLE_STUDENT")) {
            return ResponseEntity.status(403).body("Access Denied: Only students can send feedback.");
        }

        try {
            feedbackService.sendFeedbackToLecturer(studentId, request);
            return ResponseEntity.ok("Feedback sent to lecturer successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/lecturer/received")
    public ResponseEntity<?> getReceivedFeedbackForLecturer() {
        String role = (String) httpSession.getAttribute("role");
        String lecturerId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can view received feedback.");
        }

        try {
            List<Feedback> feedbacks = feedbackService.getReceivedFeedbackForLecturer(lecturerId);
            return ResponseEntity.ok(feedbacks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/student/received")
    public ResponseEntity<?> getReceivedFeedbackForStudent() {
        String role = (String) httpSession.getAttribute("role");
        String studentId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_STUDENT")) {
            return ResponseEntity.status(403).body("Access Denied: Only students can view received feedback.");
        }

        try {
            List<Feedback> feedbacks = feedbackService.getReceivedFeedbackForStudent(studentId);
            return ResponseEntity.ok(feedbacks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
