package com.example.g5be.repository;


import com.example.g5be.model.Admin;
import com.example.g5be.model.Admin;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Admin findByUsername(String username) {
        try {
            String sql = "SELECT * FROM Admin WHERE Username = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                Admin admin = new Admin();
                admin.setAid(rs.getString("AID"));
                admin.setName(rs.getString("Name"));
                admin.setUsername(rs.getString("Username"));
                admin.setPassword(rs.getString("Password")); // hashed password
                return admin;
            });
        } catch (Exception e) {
            return null; // User not found
        }
    }

    public int countAdmins() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Admin", Integer.class);
    }

    public int countLecturers() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Lecturer", Integer.class);
    }

    public int countStudents() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Student", Integer.class);
    }

    public int countEvents() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Event", Integer.class);
    }

    public int countWorkshops() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Workshop", Integer.class);
    }

    public int countAnnouncements() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Announcement", Integer.class);
    }

    public int countInterviews() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Interview", Integer.class);
    }

    public int countFeedback() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Feedback", Integer.class);
    }
}
