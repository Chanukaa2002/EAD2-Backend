package com.example.g5be.controller;

import com.example.g5be.model.Lecturer;
import com.example.g5be.service.LecturerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/lecturer")
public class LecturerController {

    private final LecturerService lecturerService;
    private final HttpSession httpSession;

    public LecturerController(LecturerService lecturerService, HttpSession httpSession) {
        this.lecturerService = lecturerService;
        this.httpSession = httpSession;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerLecturer(@RequestBody Lecturer lecturer) {
        // Check if the user is authorized as admin
        String role = (String) httpSession.getAttribute("role");

        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Access Denied: Only admins can register lecturers.");
        }

        // Proceed with registration
        lecturerService.registerLecturer(lecturer);
        return ResponseEntity.ok("Lecturer registered successfully");
    }

    @PutMapping("/{lid}")
    public ResponseEntity<String> updateLecturer(@PathVariable String lid, @RequestBody Lecturer lecturer) {
        // Check if the user is authorized as admin
        String role = (String) httpSession.getAttribute("role");

        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Access Denied: Only admins can update lecturers.");
        }

        // Proceed with update
        lecturerService.updateLecturer(lid, lecturer);
        return ResponseEntity.ok("Lecturer updated successfully");
    }

    @DeleteMapping("/remove/{lid}")
    public ResponseEntity<String> deleteLecturer(@PathVariable String lid) {
        // Check if the user is authorized as admin
        String role = (String) httpSession.getAttribute("role");

        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body("Access Denied: Only admins can delete lecturers.");
        }

        // Proceed with deletion
        lecturerService.deleteLecturer(lid);
        return ResponseEntity.ok("Lecturer deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Lecturer>> getAllLecturers() {
        // Check if the user is authorized as admin
        String role = (String) httpSession.getAttribute("role");

        if (role == null || !role.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(null);
        }

        // Retrieve all lecturers
        List<Lecturer> lecturers = lecturerService.getAllLecturers();
        return ResponseEntity.ok(lecturers);
    }
}
