package com.example.g5be.repository;


import com.example.g5be.model.Announcement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AnnouncementRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnnouncementRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save an Announcement
    public void save(Announcement announcement) {
        String sql = "INSERT INTO Announcement (EID, Description, BID) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, announcement.getEid(), announcement.getDescription(), announcement.getBid());
    }

    public List<Announcement> findAnnouncementsByLecturerId(String lecturerId) {
        String sql = """
            SELECT a.EID, a.Description, a.BID
            FROM Announcement a
            INNER JOIN Event e ON a.EID = e.EID
            WHERE e.LID = ?
            """;

        return jdbcTemplate.query(sql, new Object[]{lecturerId}, new AnnouncementRowMapper());
    }

    // RowMapper for Announcement
    static class AnnouncementRowMapper implements RowMapper<Announcement> {
        @Override
        public Announcement mapRow(ResultSet rs, int rowNum) throws SQLException {
            Announcement announcement = new Announcement();
            announcement.setEid(rs.getString("EID"));
            announcement.setDescription(rs.getString("Description"));
            announcement.setBid(rs.getString("BID"));
            return announcement;
        }
    }
}
