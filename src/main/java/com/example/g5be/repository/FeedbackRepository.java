package com.example.g5be.repository;


import com.example.g5be.model.Feedback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRepository {

    private final JdbcTemplate jdbcTemplate;

    public FeedbackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save Feedback
    public void save(Feedback feedback) {
        String sql = "INSERT INTO Feedback (FID, Sender, Receiver, Description, EID, SID, LID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, feedback.getFid(), feedback.getSender(), feedback.getReceiver(),
                feedback.getDescription(), feedback.getEid(), feedback.getSid(), feedback.getLid());
    }

    // Generate Feedback ID
    public String generateFeedbackId() {
        String sql = "SELECT FID FROM Feedback ORDER BY FID DESC LIMIT 1";
        try {
            String lastId = jdbcTemplate.queryForObject(sql, String.class);
            int nextId = Integer.parseInt(lastId.substring(1)) + 1;
            return "F" + String.format("%03d", nextId);
        } catch (Exception e) {
            return "F001"; // If no records, start with F001
        }
    }

    public List<Feedback> findReceivedFeedbackForLecturer(String lecturerId) {
        String sql = "SELECT * FROM Feedback WHERE Receiver = ?";
        return jdbcTemplate.query(sql, new Object[]{lecturerId}, (rs, rowNum) -> {
            Feedback feedback = new Feedback();
            feedback.setFid(rs.getString("FID"));
            feedback.setSender(rs.getString("Sender"));
            feedback.setReceiver(rs.getString("Receiver"));
            feedback.setDescription(rs.getString("Description"));
            feedback.setEid(rs.getString("EID"));
            feedback.setSid(rs.getString("SID"));
            feedback.setLid(rs.getString("LID"));
            return feedback;
        });
    }

    public List<Feedback> findReceivedFeedbackForStudent(String studentId) {
        String sql = "SELECT * FROM Feedback WHERE Receiver = ?";
        return jdbcTemplate.query(sql, new Object[]{studentId}, (rs, rowNum) -> {
            Feedback feedback = new Feedback();
            feedback.setFid(rs.getString("FID"));
            feedback.setSender(rs.getString("Sender"));
            feedback.setReceiver(rs.getString("Receiver"));
            feedback.setDescription(rs.getString("Description"));
            feedback.setEid(rs.getString("EID"));
            feedback.setSid(rs.getString("SID"));
            feedback.setLid(rs.getString("LID"));
            return feedback;
        });
    }
}