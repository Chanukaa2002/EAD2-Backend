package com.example.g5be.controller;

import com.example.g5be.dto.SessionDetailsDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final HttpSession httpSession;

    public SessionController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/details")
    public ResponseEntity<SessionDetailsDTO> getSessionDetails() {
        String id = (String) httpSession.getAttribute("id");
        String token = (String) httpSession.getAttribute("token");
        String role = (String) httpSession.getAttribute("role");

        // Create and return DTO
        SessionDetailsDTO sessionDetails = new SessionDetailsDTO(id, token, role);
        return ResponseEntity.ok(sessionDetails);
    }
}
