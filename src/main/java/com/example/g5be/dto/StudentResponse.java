package com.example.g5be.dto;

public class StudentResponse {
    private String sid;
    private String name;
    private String email;
    private String username;
    private String profilePic;
    private int age;
    private BadgeResponse badge;

    // Inner DTO for Badge
    public static class BadgeResponse {
        private String bid;
        private String name;

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
    }

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

    public BadgeResponse getBadge() {
        return badge;
    }

    public void setBadge(BadgeResponse badge) {
        this.badge = badge;
    }
}
