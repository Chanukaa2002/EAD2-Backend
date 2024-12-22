package com.example.g5be.repository;


import com.example.g5be.model.Lecturer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LecturerRepository {

    private final JdbcTemplate jdbcTemplate;

    public LecturerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Lecturer findByUsername(String username) {
        try {
            String sql = "SELECT * FROM Lecturer WHERE Username = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
                Lecturer lecturer = new Lecturer();
                lecturer.setLid(rs.getString("LID"));
                lecturer.setUsername(rs.getString("Username"));
                lecturer.setName(rs.getString("Name"));
                lecturer.setEmail(rs.getString("Email"));
                lecturer.setPassword(rs.getString("Password"));
                lecturer.setContact(rs.getString("Contact"));
                return lecturer;
            });
        } catch (Exception e) {
            return null;
        }
    }

    public Lecturer findById(String lid) {
        try {
            String sql = "SELECT * FROM Lecturer WHERE LID = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{lid}, (rs, rowNum) -> {
                Lecturer lecturer = new Lecturer();
                lecturer.setLid(rs.getString("LID"));
                lecturer.setUsername(rs.getString("Username"));
                lecturer.setName(rs.getString("Name"));
                lecturer.setEmail(rs.getString("Email"));
                lecturer.setPassword(rs.getString("Password"));
                lecturer.setContact(rs.getString("Contact"));
                return lecturer;
            });
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Lecturer lecturer) {
        String sql = "INSERT INTO Lecturer (LID, Username, Name, Email, Password, Contact) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, lecturer.getLid(), lecturer.getUsername(), lecturer.getName(), lecturer.getEmail(), lecturer.getPassword(), lecturer.getContact());
    }

    public void update(Lecturer lecturer) {
        String sql = "UPDATE Lecturer SET Name = ?, Email = ?, Username = ?, Password = ?, Contact = ? WHERE LID = ?";
        jdbcTemplate.update(sql, lecturer.getName(), lecturer.getEmail(), lecturer.getUsername(), lecturer.getPassword(), lecturer.getContact(), lecturer.getLid());
    }

    public void deleteById(String lid) {
        String sql = "DELETE FROM Lecturer WHERE LID = ?";
        jdbcTemplate.update(sql, lid);
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM Lecturer WHERE Username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Lecturer WHERE Email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    public String findMaxLid() {
        String sql = "SELECT MAX(LID) FROM Lecturer";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}
