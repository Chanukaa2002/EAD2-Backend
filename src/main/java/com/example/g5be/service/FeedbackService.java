package com.example.g5be.service;


import com.example.g5be.dto.FeedbackRequest;
import com.example.g5be.model.Feedback;
import com.example.g5be.repository.EventRepository;
import com.example.g5be.repository.FeedbackRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final EventRepository eventRepository;
    private final HttpSession httpSession;

    public FeedbackService(FeedbackRepository feedbackRepository, EventRepository eventRepository, HttpSession httpSession) {
        this.feedbackRepository = feedbackRepository;
        this.eventRepository = eventRepository;
        this.httpSession = httpSession;
    }

    public void sendFeedbackToStudent(FeedbackRequest request) {
        // Get the lecturer's ID from the session
        String lecturerId = (String) httpSession.getAttribute("id");

        // Create Feedback
        Feedback feedback = new Feedback();
        feedback.setFid(feedbackRepository.generateFeedbackId()); // Generate Feedback ID
        feedback.setSender(lecturerId); // Sender is the logged-in lecturer
        feedback.setReceiver(request.getSid()); // Receiver is the student
        feedback.setDescription(request.getDescription());
        feedback.setEid(request.getEid());
        feedback.setSid(request.getSid());
        feedback.setLid(lecturerId); // Lecturer ID

        // Save Feedback
        feedbackRepository.save(feedback);
    }

    public void sendFeedbackToLecturer(String studentId, FeedbackRequest request) {
        // Retrieve lecturer ID based on the event ID
        String lecturerId = eventRepository.findLecturerIdByEventId(request.getEid());

        if (lecturerId == null) {
            throw new RuntimeException("Invalid Event ID or no lecturer found.");
        }

        Feedback feedback = new Feedback();
        feedback.setFid(feedbackRepository.generateFeedbackId());
        feedback.setSender(studentId); // Sender is the logged-in student
        feedback.setReceiver(lecturerId); // Receiver is the lecturer
        feedback.setDescription(request.getDescription());
        feedback.setEid(request.getEid());
        feedback.setSid(studentId); // Student ID
        feedback.setLid(lecturerId); // Lecturer ID

        feedbackRepository.save(feedback);
    }

    public List<Feedback> getReceivedFeedbackForLecturer(String lecturerId) {
        return feedbackRepository.findReceivedFeedbackForLecturer(lecturerId);
    }

    public List<Feedback> getReceivedFeedbackForStudent(String studentId) {
        return feedbackRepository.findReceivedFeedbackForStudent(studentId);
    }
}