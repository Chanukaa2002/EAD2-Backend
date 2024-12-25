package com.example.g5be.repository;


import com.example.g5be.dto.AnnouncementDTO;
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

    public List<AnnouncementDTO> findAnnouncementsByStudentId(String studentId) {
        String sql = """
            SELECT a.EID, a.Description, a.BID, e.Date
            FROM Announcement a
            INNER JOIN Event e ON a.EID = e.EID
            INNER JOIN Badge b ON a.BID = b.bid
            INNER JOIN Student s ON s.BID = b.bid
            WHERE s.SID = ?
            ORDER BY e.Date DESC
        """;

        return jdbcTemplate.query(sql, new Object[]{studentId}, (rs, rowNum) -> {
            AnnouncementDTO announcementDTO = new AnnouncementDTO();
            announcementDTO.setEid(rs.getString("EID"));
            announcementDTO.setDescription(rs.getString("Description"));
            announcementDTO.setBid(rs.getString("BID"));
            announcementDTO.setDate(rs.getDate("Date")); // Event date
            return announcementDTO;
        });
    }
}
