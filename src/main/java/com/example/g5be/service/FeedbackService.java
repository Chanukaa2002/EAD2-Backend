package com.example.g5be.service;


import com.example.g5be.dto.FeedbackRequest;
import com.example.g5be.model.Feedback;
import com.example.g5be.repository.FeedbackRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final HttpSession httpSession;

    public FeedbackService(FeedbackRepository feedbackRepository, HttpSession httpSession) {
        this.feedbackRepository = feedbackRepository;
        this.httpSession = httpSession;
    }

    public void sendFeedback(FeedbackRequest request) {
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
}
