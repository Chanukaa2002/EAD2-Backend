package com.example.g5be.repository;

import com.example.g5be.model.Badge;
import com.example.g5be.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Find a student by username
    public Student findByUsername(String username) {
        try {
            String sql = "SELECT s.*, b.bid AS BadgeID, b.name AS BadgeName FROM Student s LEFT JOIN Badge b ON s.BID = b.bid WHERE s.Username = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                Student student = new Student();
                student.setSid(rs.getString("SID"));
                student.setName(rs.getString("Name"));
                student.setEmail(rs.getString("Email"));
                student.setUsername(rs.getString("Username"));
                student.setPassword(rs.getString("Password"));
                student.setProfilePic(rs.getString("ProfilePic"));
                student.setAge(rs.getInt("Age"));

                Badge badge = new Badge();
                badge.setBid(rs.getString("BadgeID"));
                badge.setName(rs.getString("BadgeName"));
                student.setBadge(badge);

                return student;
            });
        } catch (Exception e) {
            return null;
        }
    }

    // Save a new student
    public void save(Student student) {
        String sql = "INSERT INTO Student (SID, Name, Email, Username, Password, ProfilePic, Age, BID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                student.getSid(),
                student.getName(),
                student.getEmail(),
                student.getUsername(),
                student.getPassword(),
                student.getProfilePic(),
                student.getAge(),
                student.getBadge() != null ? student.getBadge().getBid() : null);
    }

    // Find the last student ID
    public String findLastStudentId() {
        String sql = "SELECT SID FROM Student ORDER BY SID DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            return null; // Return null if no records are found
        }
    }

    // Update a student
    public void update(Student student) {
        String sql = "UPDATE Student SET Name = ?, Email = ?, Username = ?, Password = ?, ProfilePic = ?, Age = ?, BID = ? WHERE SID = ?";
        jdbcTemplate.update(sql,
                student.getName(),
                student.getEmail(),
                student.getUsername(),
                student.getPassword(),
                student.getProfilePic(),
                student.getAge(),
                student.getBadge() != null ? student.getBadge().getBid() : null,
                student.getSid());
    }

    // Delete a student by ID
    public void deleteById(String sid) {
        String sql = "DELETE FROM Student WHERE SID = ?";
        jdbcTemplate.update(sql, sid);
    }

    public List<Student> findStudentsByBatch(String bid) {
        String sql = """
            SELECT s.SID, s.Name, s.Email, s.Username, s.Password, s.ProfilePic, s.Age, b.bid AS BadgeID, b.name AS BadgeName
            FROM Student s
            INNER JOIN Badge b ON s.BID = b.bid
            WHERE b.bid = ?
            """;

        return jdbcTemplate.query(sql, new Object[]{bid}, (rs, rowNum) -> {
            Student student = new Student();
            student.setSid(rs.getString("SID"));
            student.setName(rs.getString("Name"));
            student.setEmail(rs.getString("Email"));
            student.setUsername(rs.getString("Username"));
            student.setPassword(rs.getString("Password"));
            student.setProfilePic(rs.getString("ProfilePic"));
            student.setAge(rs.getInt("Age"));

            // Set only the bid and badge name in the badge object
            Badge badge = new Badge();
            badge.setBid(rs.getString("BadgeID"));
            badge.setName(rs.getString("BadgeName"));
            student.setBadge(badge);

            return student;
        });
    }

    public List<Student> findAll() {
        String sql = """
        SELECT s.SID, s.Name, s.Email, s.Username, s.Password, s.ProfilePic, s.Age, b.bid AS BadgeID, b.name AS BadgeName
        FROM Student s
        LEFT JOIN Badge b ON s.BID = b.bid
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Student student = new Student();
            student.setSid(rs.getString("SID"));
            student.setName(rs.getString("Name"));
            student.setEmail(rs.getString("Email"));
            student.setUsername(rs.getString("Username"));
            student.setPassword(rs.getString("Password"));
            student.setProfilePic(rs.getString("ProfilePic"));
            student.setAge(rs.getInt("Age"));

            // Set badge details
            Badge badge = new Badge();
            badge.setBid(rs.getString("BadgeID"));
            badge.setName(rs.getString("BadgeName"));
            student.setBadge(badge);

            return student;
        });
    }

}
