package com.example.g5be.dto;

public class SessionDetailsDTO {
    private String id;
    private String token;
    private String role;

    // Constructor
    public SessionDetailsDTO(String id, String token, String role) {
        this.id = id;
        this.token = token;
        this.role = role;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
