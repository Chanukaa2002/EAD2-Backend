package com.example.g5be.dto;

public class BadgeSummaryDTO {
    private String bid;       // Badge ID
    private String name;      // Batch Name
    private String course;    // Course Name
    private String status;    // Status
    private String date;      // Start Date
    private String endDate;   // End Date
    private String lecid;     // Lecturer ID

    // Constructor
    public BadgeSummaryDTO(String bid, String name, String course, String status, String date, String endDate, String lecid) {
        this.bid = bid;
        this.name = name;
        this.course = course;
        this.status = status;
        this.date = date;
        this.endDate = endDate;
        this.lecid = lecid;
    }

    // Getters and Setters
    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLecid() {
        return lecid;
    }

    public void setLecid(String lecid) {
        this.lecid = lecid;
    }
}
