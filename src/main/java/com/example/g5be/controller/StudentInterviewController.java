package com.example.g5be.controller;

import com.example.g5be.dto.StudentInterviewRequest;
import com.example.g5be.service.StudentInterviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class StudentInterviewController {

    private final StudentInterviewService studentInterviewService;
    private final HttpSession httpSession;

    public StudentInterviewController(StudentInterviewService studentInterviewService, HttpSession httpSession) {
        this.studentInterviewService = studentInterviewService;
        this.httpSession = httpSession;
    }

    @PostMapping("/lecturer/assign/interviews")
    public ResponseEntity<String> assignStudentsToInterview(@RequestBody StudentInterviewRequest request) {
        // Validate Lecturer Role
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can assign students to interviews.");
        }

        try {
            // Assign students to the interview
            studentInterviewService.assignStudentsToInterview(request.getEid(), request.getStudentIds());
            return ResponseEntity.ok("Students assigned to the interview successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
