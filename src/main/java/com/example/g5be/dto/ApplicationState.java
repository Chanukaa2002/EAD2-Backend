package com.example.g5be.dto;


public class ApplicationState {
    private int totalAdmins;
    private int totalLecturers;
    private int totalStudents;
    private int totalEvents;
    private int totalWorkshops;
    private int totalAnnouncements;
    private int totalInterviews;
    private int totalFeedback;

    public ApplicationState(int totalAdmins, int totalLecturers, int totalStudents, int totalEvents, int totalWorkshops, int totalAnnouncements, int totalInterviews, int totalFeedback) {
        this.totalAdmins = totalAdmins;
        this.totalLecturers = totalLecturers;
        this.totalStudents = totalStudents;
        this.totalEvents = totalEvents;
        this.totalWorkshops = totalWorkshops;
        this.totalAnnouncements = totalAnnouncements;
        this.totalInterviews = totalInterviews;
        this.totalFeedback = totalFeedback;
    }

    // Getters and setters
    public int getTotalAdmins() {
        return totalAdmins;
    }

    public void setTotalAdmins(int totalAdmins) {
        this.totalAdmins = totalAdmins;
    }

    public int getTotalLecturers() {
        return totalLecturers;
    }

    public void setTotalLecturers(int totalLecturers) {
        this.totalLecturers = totalLecturers;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(int totalEvents) {
        this.totalEvents = totalEvents;
    }

    public int getTotalWorkshops() {
        return totalWorkshops;
    }

    public void setTotalWorkshops(int totalWorkshops) {
        this.totalWorkshops = totalWorkshops;
    }

    public int getTotalAnnouncements() {
        return totalAnnouncements;
    }

    public void setTotalAnnouncements(int totalAnnouncements) {
        this.totalAnnouncements = totalAnnouncements;
    }

    public int getTotalInterviews() {
        return totalInterviews;
    }

    public void setTotalInterviews(int totalInterviews) {
        this.totalInterviews = totalInterviews;
    }

    public int getTotalFeedback() {
        return totalFeedback;
    }

    public void setTotalFeedback(int totalFeedback) {
        this.totalFeedback = totalFeedback;
    }
}
