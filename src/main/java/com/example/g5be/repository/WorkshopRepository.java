package com.example.g5be.repository;


import com.example.g5be.model.Workshop;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

@Repository
public class WorkshopRepository {

    private final JdbcTemplate jdbcTemplate;

    public WorkshopRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Generate the next EID for Workshop
    private String generateNextEventId() {
        String sql = "SELECT EID FROM Workshop ORDER BY EID DESC LIMIT 1";
        try {
            String lastEid = jdbcTemplate.queryForObject(sql, String.class);
            int nextId = Integer.parseInt(lastEid.substring(1)) + 1;
            return "E" + new DecimalFormat("000").format(nextId);
        } catch (Exception e) {
            // If no records exist, start with E001
            return "E001";
        }
    }

    // Save a Workshop with auto-increment EID
    public void save(Workshop workshop) {
        if (workshop.getEid() == null || workshop.getEid().isEmpty()) {
            workshop.setEid(generateNextEventId()); // Generate the next ID if not provided
        }
        String sql = "INSERT INTO Workshop (EID, Location, Topic, Speaker, Duration, BID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, workshop.getEid(), workshop.getLocation(), workshop.getTopic(), workshop.getSpeaker(), workshop.getDuration(), workshop.getBid());
    }

    // Find a Workshop by ID
    public Workshop findById(String eid) {
        String sql = "SELECT * FROM Workshop WHERE EID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{eid}, new WorkshopRowMapper());
    }

    // Find all Workshops
    public List<Workshop> findAll() {
        String sql = "SELECT * FROM Workshop";
        return jdbcTemplate.query(sql, new WorkshopRowMapper());
    }

    // Delete a Workshop
    public void deleteById(String eid) {
        String sql = "DELETE FROM Workshop WHERE EID = ?";
        jdbcTemplate.update(sql, eid);
    }

    // Workshop RowMapper
    static class WorkshopRowMapper implements RowMapper<Workshop> {
        @Override
        public Workshop mapRow(ResultSet rs, int rowNum) throws SQLException {
            Workshop workshop = new Workshop();
            workshop.setEid(rs.getString("EID"));
            workshop.setLocation(rs.getString("Location"));
            workshop.setTopic(rs.getString("Topic"));
            workshop.setSpeaker(rs.getString("Speaker"));
            workshop.setDuration(rs.getString("Duration"));
            workshop.setBid(rs.getString("BID"));
            return workshop;
        }
    }

    public List<Workshop> findWorkshopsByLecturerId(String lecturerId) {
        String sql = """
                SELECT w.EID, w.Location, w.Topic, w.Speaker, w.Duration, w.BID
                FROM Workshop w
                INNER JOIN Event e ON w.EID = e.EID
                INNER JOIN Lecturer l ON e.LID = l.LID
                WHERE l.LID = ?
                """;

        return jdbcTemplate.query(sql, new Object[]{lecturerId}, new WorkshopRowMapper());
    }


}
