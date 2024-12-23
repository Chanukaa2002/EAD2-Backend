package com.example.g5be.service;


import com.example.g5be.model.StudentInterview;
import com.example.g5be.repository.StudentInterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentInterviewService {

    private final StudentInterviewRepository studentInterviewRepository;

    public StudentInterviewService(StudentInterviewRepository studentInterviewRepository) {
        this.studentInterviewRepository = studentInterviewRepository;
    }

    @Transactional
    public void assignStudentsToInterview(String eid, List<String> studentIds) {
        for (String sid : studentIds) {
            StudentInterview studentInterview = new StudentInterview();
            studentInterview.setEid(eid);
            studentInterview.setSid(sid);
            studentInterviewRepository.save(studentInterview);
        }
    }
}
