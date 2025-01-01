package com.example.g5be.controller;

import com.example.g5be.dto.StudentResponse;
import com.example.g5be.model.Student;
import com.example.g5be.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;
    private final HttpSession httpSession;

    public StudentController(StudentService studentService, HttpSession httpSession) {
        this.studentService = studentService;
        this.httpSession = httpSession;
    }

    // Register a new student
    @PostMapping("/admin/student/register")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        studentService.registerStudent(student);
        return ResponseEntity.ok("Student registered successfully");
    }

    // Update an existing student
    @PutMapping("/admin/student/{sid}")
    public ResponseEntity<String> updateStudent(@PathVariable String sid, @RequestBody Student student) {
        studentService.updateStudent(sid, student);
        return ResponseEntity.ok("Student updated successfully");
    }

    // Delete a student by ID
    @DeleteMapping("/admin/student/remove/{sid}")
    public ResponseEntity<String> deleteStudent(@PathVariable String sid) {
        studentService.deleteStudent(sid);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // Handle access denied
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleAccessDenied(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @GetMapping("/lecturer/students/{bid}")/// //
    public ResponseEntity<?> getStudentsByBatch(@PathVariable String bid) {
        try {
            // Fetch students for the batch
            List<StudentResponse> students = studentService.getStudentsByBatch(bid);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/students/profile")
    public ResponseEntity<String> updateProfile(@RequestBody Student student) {
        // Get the student ID from session
        String role = (String) httpSession.getAttribute("role");
        String studentId = (String) httpSession.getAttribute("id");

        if (role == null || !role.equals("ROLE_STUDENT")) {
            return ResponseEntity.status(403).body("Access Denied: Only students can update their profile.");
        }

        try {
            studentService.updateStudentProfile(studentId, student);
            return ResponseEntity.ok("Student profile updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/admin/students")
    public ResponseEntity<?> getAllStudents() {
        try {
            String role = (String) httpSession.getAttribute("role");
            if (role == null || !role.equals("ROLE_ADMIN")) {
                throw new RuntimeException("Access Denied: Only admins can perform this action.");
            }

            // Fetch all students
            List<StudentResponse> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e.getMessage());
        }
    }
}
