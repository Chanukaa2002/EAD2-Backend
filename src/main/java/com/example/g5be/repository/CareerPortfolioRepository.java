package com.example.g5be.repository;


import com.example.g5be.model.CareerPortfolio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CareerPortfolioRepository {

    private final JdbcTemplate jdbcTemplate;

    public CareerPortfolioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save a CareerPortfolio
    public void save(CareerPortfolio portfolio) {
        String sql = "INSERT INTO CareerPortfolio (CID, Description, Education, Achievements, GithubUsername, SID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                portfolio.getCid(),
                portfolio.getDescription(),
                portfolio.getEducation(),
                portfolio.getAchievements(),
                portfolio.getGithubUsername(),
                portfolio.getSid());
    }

    // Find the last CareerPortfolio ID
    public String findLastCareerPortfolioId() {
        String sql = "SELECT CID FROM CareerPortfolio ORDER BY CID DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            return null; // Return null if no records are found
        }
    }

    // Delete a CareerPortfolio by Student ID
    public void deleteByStudentId(String sid) {
        String sql = "DELETE FROM CareerPortfolio WHERE SID = ?";
        jdbcTemplate.update(sql, sid);
    }

    public void update(CareerPortfolio portfolio) {
        String sql = """
            UPDATE CareerPortfolio
            SET Description = ?, Education = ?, Achievements = ?, GithubUsername = ?
            WHERE SID = ?
        """;
        jdbcTemplate.update(
                sql,
                portfolio.getDescription(),
                portfolio.getEducation(),
                portfolio.getAchievements(),
                portfolio.getGithubUsername(),
                portfolio.getSid()
        );
    }


    public Map<String, Object> findPortfolioAndStudentByStudentId(String studentId) {
        String sql = """
        SELECT 
            s.sid AS studentId,
            s.name AS studentName,
            s.email AS studentEmail,
            s.username AS studentUsername,
            s.profilePic AS studentProfilePic,
            s.age AS studentAge,
            cp.cid AS portfolioId,
            cp.description AS portfolioDescription,
            cp.education AS portfolioEducation,
            cp.achievements AS portfolioAchievements,
            cp.githubUsername AS portfolioGithubUsername
        FROM Student s
        LEFT JOIN CareerPortfolio cp ON s.sid = cp.sid
        WHERE s.sid = ?
    """;

        return jdbcTemplate.queryForObject(sql, new Object[]{studentId}, (rs, rowNum) -> {
            Map<String, Object> result = new HashMap<>();
            result.put("studentId", rs.getString("studentId"));
            result.put("studentName", rs.getString("studentName"));
            result.put("studentEmail", rs.getString("studentEmail"));
            result.put("studentUsername", rs.getString("studentUsername"));
            result.put("studentProfilePic", rs.getString("studentProfilePic"));
            result.put("studentAge", rs.getInt("studentAge"));
            result.put("portfolioId", rs.getString("portfolioId"));
            result.put("portfolioDescription", rs.getString("portfolioDescription"));
            result.put("portfolioEducation", rs.getString("portfolioEducation"));
            result.put("portfolioAchievements", rs.getString("portfolioAchievements"));
            result.put("portfolioGithubUsername", rs.getString("portfolioGithubUsername"));
            return result;
        });
    }
}
