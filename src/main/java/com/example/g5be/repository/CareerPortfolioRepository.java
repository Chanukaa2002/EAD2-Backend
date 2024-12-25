package com.example.g5be.repository;


import com.example.g5be.model.CareerPortfolio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
