package com.example.g5be.controller;

import com.example.g5be.dto.WorkshopRequest;
import com.example.g5be.dto.WorkshopResponse;
import com.example.g5be.model.Event;
import com.example.g5be.model.Workshop;
import com.example.g5be.service.EventService;
import com.example.g5be.service.WorkshopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class WorkshopController {

    private final EventService eventService;
    private final WorkshopService workshopService;
    private final HttpSession httpSession;

    public WorkshopController(EventService eventService, WorkshopService workshopService, HttpSession httpSession) {
        this.eventService = eventService;
        this.workshopService = workshopService;
        this.httpSession = httpSession;
    }

    @PostMapping("/lecturer/events/create/workshop")
    public ResponseEntity<String> createWorkshop(@RequestBody WorkshopRequest request) {
        // Check if the user is a lecturer
        String role = (String) httpSession.getAttribute("role");
        String lecturerId = (String) httpSession.getAttribute("id"); // Get LID from session
        if (role == null || !role.equals("ROLE_LECTURER")) {
            return ResponseEntity.status(403).body("Access Denied: Only lecturers can create workshops.");
        }

        try {
            // Create the Event
            Event event = new Event();
            event.setEid(request.getEid());
            event.setDate(request.getDate());
            event.setName(request.getName());
            event.setStatus(request.getStatus());
            event.setLid(lecturerId); // Automatically assign LID from session

            // Create the Workshop
            Workshop workshop = new Workshop();
            workshop.setEid(request.getEid()); // Same EID as the event
            workshop.setLocation(request.getLocation());
            workshop.setTopic(request.getTopic());
            workshop.setSpeaker(request.getSpeaker());
            workshop.setDuration(request.getDuration());
            workshop.setBid(request.getBid()); // Badge ID

            // Use transactional service to save both
            workshopService.createWorkshopWithEvent(event, workshop);

            return ResponseEntity.ok("Workshop and associated Event created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/workshops")
    public ResponseEntity<?> getWorkshopsForLecturer() {
        // Check if the user is a lecturer
        String role = (String) httpSession.getAttribute("role");
        String studentId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_STUDENT")) {
            return ResponseEntity.status(403).body("Access Denied: Only students can view their workshops.");
        }

        try {
            List<WorkshopResponse> workshops = workshopService.getWorkshopsByStudentId(studentId);
            return ResponseEntity.ok(workshops);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
