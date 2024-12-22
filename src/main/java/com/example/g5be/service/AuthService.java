package com.example.g5be.service;


import com.example.g5be.config.JwtUtil;
import com.example.g5be.model.Admin;
import com.example.g5be.model.Lecturer;
import com.example.g5be.model.Student;
import com.example.g5be.repository.AdminRepository;
import com.example.g5be.repository.LecturerRepository;
import com.example.g5be.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final JwtUtil jwtUtil;
    private final HttpSession httpSession;

    public AuthService(AdminRepository adminRepository,
                       LecturerRepository lecturerRepository,
                       StudentRepository studentRepository,
                       JwtUtil jwtUtil,
                       HttpSession httpSession) {
        this.adminRepository = adminRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
        this.httpSession = httpSession;
    }

    // Authenticate Admin
    public String authenticateAdmin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(admin.getAid());
            storeSession(admin.getAid(), token, "ROLE_ADMIN");
            return token;
        }
        throw new RuntimeException("Invalid Admin username or password");
    }

    // Authenticate Lecturer
    public String authenticateLecturer(String username, String password) {
        Lecturer lecturer = lecturerRepository.findByUsername(username);
        if (lecturer != null && lecturer.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(lecturer.getLid());
            storeSession(lecturer.getLid(), token, "ROLE_LECTURER");
            return token;
        }
        throw new RuntimeException("Invalid Lecturer username or password");
    }

    // Authenticate Student
    public String authenticateStudent(String username, String password) {
        Student student = studentRepository.findByUsername(username);
        if (student != null && student.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(student.getSid());
            storeSession(student.getSid(), token, "ROLE_STUDENT");
            return token;
        }
        throw new RuntimeException("Invalid Student username or password");
    }

    // Store user info in the session
    private void storeSession(String id, String token, String role) {
        httpSession.setAttribute("id", id);
        httpSession.setAttribute("token", token);
        httpSession.setAttribute("role", role);
    }
}
