package com.example.g5be.model;


public class Announcement {
    private String eid;          // Primary Key and Foreign Key referencing Event
    private String description;  // Description of the announcement
    private String bid;          // Foreign Key referencing Badge

    // Getters and Setters
    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
