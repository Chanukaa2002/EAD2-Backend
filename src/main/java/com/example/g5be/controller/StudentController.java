package com.example.g5be.controller;


import com.example.g5be.model.Student;
import com.example.g5be.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        studentService.registerStudent(student);
        return ResponseEntity.ok("Student registered successfully");
    }

    @PutMapping("/{sid}")
    public ResponseEntity<String> updateStudent(@PathVariable String sid, @RequestBody Student student) {
        studentService.updateStudent(sid, student);
        return ResponseEntity.ok("Student updated successfully");
    }

    // Delete a student by ID
    @DeleteMapping("/remove/{sid}")
    public ResponseEntity<String> deleteStudent(@PathVariable String sid) {
        studentService.deleteStudent(sid);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // Handle access denied
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleAccessDenied(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
