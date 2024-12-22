package com.example.g5be.service;


import com.example.g5be.model.Lecturer;
import com.example.g5be.repository.LecturerRepository;
import org.springframework.stereotype.Service;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public void registerLecturer(Lecturer lecturer) {
        // Generate the next LID
        String nextLid = generateNextLid();
        lecturer.setLid(nextLid);

        // Validate if Username or Email already exists
        if (lecturerRepository.existsByUsername(lecturer.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (lecturerRepository.existsByEmail(lecturer.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Save Lecturer to the database
        lecturerRepository.save(lecturer);
    }

    public void updateLecturer(String lid, Lecturer updatedLecturer) {
        // Find the existing lecturer
        Lecturer existingLecturer = lecturerRepository.findById(lid);
        if (existingLecturer == null) {
            throw new RuntimeException("Lecturer not found");
        }

        // Update the lecturer's details
        existingLecturer.setName(updatedLecturer.getName());
        existingLecturer.setEmail(updatedLecturer.getEmail());
        existingLecturer.setUsername(updatedLecturer.getUsername());
        existingLecturer.setPassword(updatedLecturer.getPassword());
        existingLecturer.setContact(updatedLecturer.getContact());

        // Save the updated lecturer
        lecturerRepository.update(existingLecturer);
    }

    public void deleteLecturer(String lid) {
        // Check if the lecturer exists
        Lecturer lecturer = lecturerRepository.findById(lid);
        if (lecturer == null) {
            throw new RuntimeException("Lecturer not found");
        }

        // Delete the lecturer
        lecturerRepository.deleteById(lid);
    }

    private String generateNextLid() {
        String currentMaxLid = lecturerRepository.findMaxLid();
        if (currentMaxLid == null) {
            return "L001"; // Start from L001 if no existing lecturers
        }
        int currentMaxId = Integer.parseInt(currentMaxLid.substring(1)); // Extract numeric part
        int nextId = currentMaxId + 1;
        return String.format("L%03d", nextId); // Format as L001, L002, etc.
    }
}
