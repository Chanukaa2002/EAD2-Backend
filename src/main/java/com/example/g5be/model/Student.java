package com.example.g5be.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    private String sid;

    private String name;

    private String email;

    private String username;

    private String password;

    private String profilePic;

    private int age;

    @ManyToOne
    @JoinColumn(name = "BID", referencedColumnName = "bid", nullable = false) // Mapping to Badge table
    private Badge badge; // Foreign key reference to Badge table

    // Getters and Setters
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
}
