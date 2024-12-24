package com.example.g5be.service;

import com.example.g5be.dto.ApplicationState;
import com.example.g5be.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public ApplicationState getApplicationState() {
        int totalAdmins = adminRepository.countAdmins();
        int totalLecturers = adminRepository.countLecturers();
        int totalStudents = adminRepository.countStudents();
        int totalEvents = adminRepository.countEvents();
        int totalWorkshops = adminRepository.countWorkshops();
        int totalAnnouncements = adminRepository.countAnnouncements();
        int totalInterviews = adminRepository.countInterviews();
        int totalFeedback = adminRepository.countFeedback();

        return new ApplicationState(totalAdmins, totalLecturers, totalStudents, totalEvents, totalWorkshops, totalAnnouncements, totalInterviews, totalFeedback);
    }
}
