package com.example.g5be.repository;


import com.example.g5be.model.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Repository
public class EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Generate the next EID for Event
    private String generateNextEventId() {
        String sql = "SELECT EID FROM Event ORDER BY EID DESC LIMIT 1";
        try {
            String lastEid = jdbcTemplate.queryForObject(sql, String.class);
            int nextId = Integer.parseInt(lastEid.substring(1)) + 1;
            return "E" + new DecimalFormat("000").format(nextId);
        } catch (Exception e) {
            // If no records exist, start with E001
            return "E001";
        }
    }

    // Save an Event with auto-increment EID
    public void save(Event event) {
        if (event.getEid() == null || event.getEid().isEmpty()) {
            event.setEid(generateNextEventId()); // Generate the next ID if not provided
        }
        String sql = "INSERT INTO Event (EID, Date, Name, Status, LID) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, event.getEid(), event.getDate(), event.getName(), event.getStatus(), event.getLid());
    }

    // Find an Event by ID
    public Event findById(String eid) {
        String sql = "SELECT * FROM Event WHERE EID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{eid}, new EventRowMapper());
    }

    // Find all Events
    public List<Event> findAll() {
        String sql = "SELECT * FROM Event";
        return jdbcTemplate.query(sql, new EventRowMapper());
    }

    public void updateStatus(String eid, String status) {
        String sql = "UPDATE Event SET Status = ? WHERE EID = ?";
        jdbcTemplate.update(sql, status, eid);
    }

    // Delete an Event
    public void deleteById(String eid) {
        String sql = "DELETE FROM Event WHERE EID = ?";
        jdbcTemplate.update(sql, eid);
    }

    // Event RowMapper
    static class EventRowMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setEid(rs.getString("EID"));
            event.setDate(rs.getDate("Date"));
            event.setName(rs.getString("Name"));
            event.setStatus(rs.getString("Status"));
            event.setLid(rs.getString("LID"));
            return event;
        }
    }

    public List<Event> findEventsByStatus(String status, Date today) {
        String sql;
        if (status.equalsIgnoreCase("Complete")) {
            sql = "SELECT * FROM Event WHERE Date < ?";
        } else {
            sql = "SELECT * FROM Event WHERE Date >= ?";
        }
        return jdbcTemplate.query(sql, new Object[]{today}, this::mapRowToEvent);
    }



    private Event mapRowToEvent(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();
        event.setEid(rs.getString("EID"));
        event.setDate(rs.getDate("Date"));
        event.setName(rs.getString("Name"));
        event.setStatus(rs.getString("Status"));
        event.setLid(rs.getString("LID"));
        return event;
    }
}
