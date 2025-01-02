package com.example.g5be.repository;


import com.example.g5be.dto.InterviewDTO;
import com.example.g5be.dto.StudentDTO;
import com.example.g5be.model.Interview;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InterviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public InterviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save an Interview
    public void save(Interview interview) {
        String sql = "INSERT INTO Interview (EID, Interviewer, Location) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, interview.getEid(), interview.getInterviewer(), interview.getLocation());
    }

    public List<InterviewDTO> findInterviewsByLecturerId(String lecturerId) {
        String sql = """
            SELECT i.EID, i.Interviewer, i.Location, e.Date
            FROM Interview i
            INNER JOIN Event e ON i.EID = e.EID
            WHERE e.LID = ?
            """;

        return jdbcTemplate.query(sql, new Object[]{lecturerId}, (rs, rowNum) -> {
            InterviewDTO interviewDTO = new InterviewDTO();
            interviewDTO.setEid(rs.getString("EID"));
            interviewDTO.setInterviewer(rs.getString("Interviewer"));
            interviewDTO.setLocation(rs.getString("Location"));
            interviewDTO.setDate(rs.getDate("Date")); // Set the interview date
            return interviewDTO;
        });
    }


    public List<InterviewDTO> findInterviewsByStudentId(String studentId) {
        String sql = """
            SELECT i.EID, i.Interviewer, i.Location, e.Date
            FROM Student_Interview si
            INNER JOIN Interview i ON si.EID = i.EID
            INNER JOIN Event e ON i.EID = e.EID
            WHERE si.SID = ?
             ORDER BY e.Date DESC
            """;

        return jdbcTemplate.query(sql, new Object[]{studentId}, (rs, rowNum) -> {
            InterviewDTO interviewDTO = new InterviewDTO();
            interviewDTO.setEid(rs.getString("EID"));
            interviewDTO.setInterviewer(rs.getString("Interviewer"));
            interviewDTO.setLocation(rs.getString("Location"));
            interviewDTO.setDate(rs.getDate("Date")); // Set the interview date
            return interviewDTO;
        });
    }


    public List<StudentDTO> findStudentsByEventId(String eventId) {
        String sql = """
        SELECT s.SID AS id, s.Name AS name, s.Email AS email
        FROM Student_Interview si
        INNER JOIN Student s ON si.SID = s.SID
        WHERE si.EID = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{eventId}, (rs, rowNum) ->
                new StudentDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email")
                )
        );
    }


}