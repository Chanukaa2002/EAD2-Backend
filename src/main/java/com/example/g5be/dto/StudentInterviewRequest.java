package com.example.g5be.dto;

import java.util.List;

public class StudentInterviewRequest {
    private String eid; // Event ID (Interview ID)
    private List<String> studentIds; // List of Student IDs to assign

    // Getters and Setters
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }
}
