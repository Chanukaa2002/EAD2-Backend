package com.example.g5be.service;

import com.example.g5be.dto.StudentResponse;
import com.example.g5be.model.Student;
import com.example.g5be.repository.StudentRepository;
import com.example.g5be.service.CareerPortfolioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CareerPortfolioService careerPortfolioService;
    private final HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;


    public StudentService(StudentRepository studentRepository, CareerPortfolioService careerPortfolioService, HttpSession httpSession,PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.careerPortfolioService = careerPortfolioService;
        this.httpSession = httpSession;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerStudent(Student student) {
        checkAdminAccess();

        // Generate the next student ID
        String nextId = generateNextStudentId();
        student.setSid(nextId);

        //hashing password
        String hashedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(hashedPassword);

        // Save the student
        studentRepository.save(student);

        // Create a CareerPortfolio entry
        careerPortfolioService.createCareerPortfolio(nextId);
    }

    public void updateStudent(String sid, Student updatedStudent) {
        checkAdminAccess();

        // Ensure the SID is set correctly
        updatedStudent.setSid(sid);

        // Encode the password if it is not null or empty
        if (updatedStudent.getPassword() != null && !updatedStudent.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(updatedStudent.getPassword());
            updatedStudent.setPassword(hashedPassword);
        }

        // Update the student
        studentRepository.update(updatedStudent);
    }


    public void deleteStudent(String sid) {
        checkAdminAccess();

        // Delete the student
        studentRepository.deleteById(sid);

        // Delete the corresponding CareerPortfolio
        careerPortfolioService.deleteCareerPortfolioByStudentId(sid);
    }

    private void checkAdminAccess() {
        String role = (String) httpSession.getAttribute("role");
        if (role == null || !role.equals("ROLE_ADMIN")) {
            throw new RuntimeException("Access Denied: Only admins can perform this action.");
        }
    }

    private String generateNextStudentId() {
        String lastId = studentRepository.findLastStudentId();
        if (lastId == null || lastId.isEmpty()) {
            return "S001";
        }
        int idNumber = Integer.parseInt(lastId.substring(1)); // Remove "S" prefix
        return String.format("S%03d", idNumber + 1); // Format as "S001", "S002", etc.
    }

    public List<StudentResponse> getStudentsByBatch(String bid) {
        List<Student> students = studentRepository.findStudentsByBatch(bid);

        // Convert Student to StudentResponse
        return students.stream().map(student -> {
            StudentResponse response = new StudentResponse();
            response.setSid(student.getSid());
            response.setName(student.getName());
            response.setEmail(student.getEmail());
            response.setUsername(student.getUsername());
            response.setProfilePic(student.getProfilePic());
            response.setAge(student.getAge());

            // Set badge details
            if (student.getBadge() != null) {
                StudentResponse.BadgeResponse badgeResponse = new StudentResponse.BadgeResponse();
                badgeResponse.setBid(student.getBadge().getBid());
                badgeResponse.setName(student.getBadge().getName());
                response.setBadge(badgeResponse);
            }

            return response;
        }).toList();
    }

    public void updateStudentProfile(String studentId, Student updatedStudent) {
        // Ensure the SID is set correctly
        updatedStudent.setSid(studentId);

        // Encode the password if it is not null or empty
        if (updatedStudent.getPassword() != null && !updatedStudent.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(updatedStudent.getPassword());
            updatedStudent.setPassword(hashedPassword);
        }

        // Update the student in the database
        studentRepository.update(updatedStudent);
    }


    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        // Convert Student to StudentResponse
        return students.stream().map(student -> {
            StudentResponse response = new StudentResponse();
            response.setSid(student.getSid());
            response.setName(student.getName());
            response.setEmail(student.getEmail());
            response.setUsername(student.getUsername());
            response.setProfilePic(student.getProfilePic());
            response.setAge(student.getAge());

            // Set badge details
            if (student.getBadge() != null) {
                StudentResponse.BadgeResponse badgeResponse = new StudentResponse.BadgeResponse();
                badgeResponse.setBid(student.getBadge().getBid());
                badgeResponse.setName(student.getBadge().getName());
                response.setBadge(badgeResponse);
            }

            return response;
        }).toList();
    }
}