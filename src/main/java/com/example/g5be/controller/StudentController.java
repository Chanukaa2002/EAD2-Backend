package com.example.g5be.controller;

import com.example.g5be.dto.StudentResponse;
import com.example.g5be.model.Student;
import com.example.g5be.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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


}
