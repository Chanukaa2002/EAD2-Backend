package com.example.g5be.model;


public class StudentInterview {
    private String eid; // Foreign key referencing Interview
    private String sid; // Foreign key referencing Student

    // Getters and Setters
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
