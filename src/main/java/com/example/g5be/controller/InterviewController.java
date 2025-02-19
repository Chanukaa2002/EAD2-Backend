package com.example.g5be.controller;

import com.example.g5be.dto.InterviewDTO;
import com.example.g5be.dto.InterviewRequest;
import com.example.g5be.dto.StudentDTO;
import com.example.g5be.model.Event;
import com.example.g5be.model.Interview;
import com.example.g5be.service.InterviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InterviewController {

    private final InterviewService interviewService;
    private final HttpSession httpSession;

    public InterviewController(InterviewService interviewService, HttpSession httpSession) {
        this.interviewService = interviewService;
        this.httpSession = httpSession;
    }

    @PostMapping("/lecturer/events/create/interview")
    public ResponseEntity<String> createInterview(@RequestBody InterviewRequest request) {
        // Validate Lecturer Role
        String role = (String) httpSession.getAttribute("role");
        String lecturerId = (String) httpSession.getAttribute("id");
        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can create interviews.");
        }

        try {
            // Create Event
            Event event = new Event();
            event.setEid(request.getEid());
            event.setDate(request.getDate());
            event.setName(request.getName());
            event.setStatus(request.getStatus());
            event.setLid(lecturerId);

            // Create Interview
            Interview interview = new Interview();
            interview.setInterviewer(request.getInterviewer());
            interview.setLocation(request.getLocation());

            // Save both Event and Interview
            interviewService.createInterviewWithEvent(event, interview);

            return ResponseEntity.ok("Interview and associated Event created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/lecturer/interviews")
    public ResponseEntity<?> getInterviewsForLoggedLecturer() {
        // Validate Lecturer Role
        String role = (String) httpSession.getAttribute("role");
        String lecturerId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can view their interviews.");
        }

        try {
            // Fetch interviews for the logged-in lecturer
            List<InterviewDTO> interviews = interviewService.getInterviewsByLecturerId(lecturerId);
            return ResponseEntity.ok(interviews);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/students/interviews")//added new
    public ResponseEntity<?> getInterviewsForLoggedStudent() {
        // Validate Student Role
        String role = (String) httpSession.getAttribute("role");
        String studentId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_STUDENT")) {
            return ResponseEntity.status(403).body("Access Denied: Only students can view their interviews.");
        }

        try {
            // Fetch interviews assigned to the logged-in student
            List<InterviewDTO> interviews = interviewService.getInterviewsByStudentId(studentId);
            return ResponseEntity.ok(interviews);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/interview/{eventId}/students")
    public ResponseEntity<?> getStudentsForInterview(@PathVariable String eventId) {
        // Validate Lecturer Role
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can view assigned students.");
        }

        try {
            // Fetch students as DTOs
            List<StudentDTO> students = interviewService.getStudentsByEventId(eventId);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
