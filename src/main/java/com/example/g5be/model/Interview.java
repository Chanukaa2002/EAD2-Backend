package com.example.g5be.model;


public class Interview {
    private String eid; // Primary key, foreign key referencing Event
    private String interviewer;
    private String location;

    // Getters and Setters
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
