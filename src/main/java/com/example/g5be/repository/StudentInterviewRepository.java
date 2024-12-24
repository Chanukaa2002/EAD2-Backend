package com.example.g5be.repository;

import com.example.g5be.model.StudentInterview;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentInterviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentInterviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save a Student-Interview mapping
    public void save(StudentInterview studentInterview) {
        String sql = "INSERT INTO Student_Interview (EID, SID) VALUES (?, ?)";
        jdbcTemplate.update(sql, studentInterview.getEid(), studentInterview.getSid());
    }
}
