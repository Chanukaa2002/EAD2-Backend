package com.example.g5be.repository;


import com.example.g5be.model.Feedback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
