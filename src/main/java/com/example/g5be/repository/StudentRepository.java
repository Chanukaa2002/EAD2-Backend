package com.example.g5be.repository;


import com.example.g5be.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Find a student by username
    public Student findByUsername(String username) {
        try {
            String sql = "SELECT * FROM Student WHERE Username = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                Student student = new Student();
                student.setSid(rs.getString("SID"));
                student.setName(rs.getString("Name"));
                student.setEmail(rs.getString("Email"));
                student.setUsername(rs.getString("Username"));
                student.setPassword(rs.getString("Password"));
                student.setProfilePic(rs.getString("ProfilePic"));
                student.setAge(rs.getInt("Age"));
                student.setBid(rs.getString("BID"));
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
                student.getBid());
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
                student.getBid(),
                student.getSid());
    }

    // Delete a student by ID
    public void deleteById(String sid) {
        String sql = "DELETE FROM Student WHERE SID = ?";
        jdbcTemplate.update(sql, sid);
    }
}
