package com.example.g5be.service;

import com.example.g5be.model.Event;
import com.example.g5be.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public void updateEventStatus(String eid, String status) {
        eventRepository.updateStatus(eid, status);
    }

    public void deleteById(String eid) {
        eventRepository.deleteById(eid);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findCompletedEvents() {
        Date today = new Date();
        return eventRepository.findEventsByStatus("Complete", today);
    }

    public List<Event> findUpcomingEvents() {
        Date today = new Date();
        return eventRepository.findEventsByStatus("Upcoming", today);
    }


    public List<Event> findEventsByLecturerId(String lecturerId) {
        return eventRepository.findEventsByLecturerId(lecturerId);
    }

}
