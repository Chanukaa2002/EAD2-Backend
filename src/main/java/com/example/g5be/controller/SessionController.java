package com.example.g5be.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final HttpSession httpSession;

    public SessionController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }


    @GetMapping("/details")
    public String getSessionDetails() {
        String id = (String) httpSession.getAttribute("id");
        String token = (String) httpSession.getAttribute("token");
        String role = (String) httpSession.getAttribute("role");
        return "ID: " + id + ", Token: " + token + ", Role: " + role;
    }
}

