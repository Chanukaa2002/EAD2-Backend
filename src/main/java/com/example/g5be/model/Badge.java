package com.example.g5be.model;


import jakarta.persistence.*;
import java.util.Date;
@Entity
@Table(name = "Badge")
public class Badge {

    @Id
    private String bid;

    private String name;
    private String course;
    private String status;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.DATE)
    @Column(name = "EndDate")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "LID", referencedColumnName = "LID", nullable = false)
    private Lecturer lecturer;


    // Getters and setters
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
}
