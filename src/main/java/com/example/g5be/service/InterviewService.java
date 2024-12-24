package com.example.g5be.service;


import com.example.g5be.dto.InterviewDTO;
import com.example.g5be.model.Event;
import com.example.g5be.model.Interview;
import com.example.g5be.repository.EventRepository;
import com.example.g5be.repository.InterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InterviewService {

    private final EventRepository eventRepository;
    private final InterviewRepository interviewRepository;

    public InterviewService(EventRepository eventRepository, InterviewRepository interviewRepository) {
        this.eventRepository = eventRepository;
        this.interviewRepository = interviewRepository;
    }

    @Transactional
    public void createInterviewWithEvent(Event event, Interview interview) {
        // Save the Event
        eventRepository.save(event);

        // Ensure the Interview uses the same EID as the Event
        interview.setEid(event.getEid());

        // Save the Interview
        interviewRepository.save(interview);
    }

    public List<InterviewDTO> getInterviewsByLecturerId(String lecturerId) {
        return interviewRepository.findInterviewsByLecturerId(lecturerId);
    }


}
