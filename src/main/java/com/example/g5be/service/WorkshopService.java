package com.example.g5be.service;


import com.example.g5be.dto.StudentDTO;
import com.example.g5be.dto.WorkshopResponse;
import com.example.g5be.model.Event;
import com.example.g5be.model.Workshop;
import com.example.g5be.repository.EventRepository;
import com.example.g5be.repository.WorkshopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkshopService {

    private final EventRepository eventRepository;
    private final WorkshopRepository workshopRepository;

    public WorkshopService(EventRepository eventRepository, WorkshopRepository workshopRepository) {
        this.eventRepository = eventRepository;
        this.workshopRepository = workshopRepository;
    }

    @Transactional
    public void createWorkshopWithEvent(Event event, Workshop workshop) {
        // Save the Event
        eventRepository.save(event);

        // Set the workshop's ID (EID) as the same as the event's ID
        workshop.setEid(event.getEid());

        // Save the Workshop
        workshopRepository.save(workshop);
    }

    public List<WorkshopResponse> getWorkshopsByLecturerId(String lecturerId) {
        return workshopRepository.findWorkshopsByLecturerId(lecturerId);
    }

    public List<WorkshopResponse> getWorkshopsByStudentId(String studentId) {
        return workshopRepository.findWorkshopsByStudentId(studentId);
    }


    public List<StudentDTO> getStudentsByWorkshopEventId(String eventId) {
        return workshopRepository.findStudentsByWorkshopEventId(eventId);
    }

}
