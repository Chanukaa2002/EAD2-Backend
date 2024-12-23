package com.example.g5be.service;


import com.example.g5be.model.Announcement;
import com.example.g5be.model.Event;
import com.example.g5be.repository.AnnouncementRepository;
import com.example.g5be.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date; // Import for java.sql.Date
import java.time.LocalDate; // Import for java.time.LocalDate
import java.util.List;

@Service
public class AnnouncementService {

    private final EventRepository eventRepository;
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(EventRepository eventRepository, AnnouncementRepository announcementRepository) {
        this.eventRepository = eventRepository;
        this.announcementRepository = announcementRepository;
    }

    @Transactional
    public void createAnnouncementWithEvent(Event event, Announcement announcement) {
        // Set the event date as the current date
        event.setDate(Date.valueOf(LocalDate.now()));

        // Save the Event first
        eventRepository.save(event);

        // Ensure the Announcement uses the same EID as the Event
        announcement.setEid(event.getEid());

        // Save the Announcement
        announcementRepository.save(announcement);
    }

    public List<Announcement> getAnnouncementsByLecturerId(String lecturerId) {
        return announcementRepository.findAnnouncementsByLecturerId(lecturerId);
    }
}
