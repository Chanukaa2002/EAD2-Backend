package com.example.g5be.service;


import com.example.g5be.model.CareerPortfolio;
import com.example.g5be.repository.CareerPortfolioRepository;
import org.springframework.stereotype.Service;

@Service
public class CareerPortfolioService {

    private final CareerPortfolioRepository careerPortfolioRepository;

    public CareerPortfolioService(CareerPortfolioRepository careerPortfolioRepository) {
        this.careerPortfolioRepository = careerPortfolioRepository;
    }

    // Create a new CareerPortfolio entry
    public void createCareerPortfolio(String studentId) {
        CareerPortfolio careerPortfolio = new CareerPortfolio();
        careerPortfolio.setCid(generateNextCareerPortfolioId());
        careerPortfolio.setSid(studentId);
        careerPortfolio.setDescription(null);
        careerPortfolio.setEducation(null);
        careerPortfolio.setAchievements(null);
        careerPortfolio.setGithubUsername(null);

        careerPortfolioRepository.save(careerPortfolio);
    }

    // Delete CareerPortfolio by Student ID
    public void deleteCareerPortfolioByStudentId(String studentId) {
        careerPortfolioRepository.deleteByStudentId(studentId);
    }

    // Generate the next CareerPortfolio ID
    private String generateNextCareerPortfolioId() {
        String lastCid = careerPortfolioRepository.findLastCareerPortfolioId();
        if (lastCid == null || lastCid.isEmpty()) {
            return "C001";
        }
        int idNumber = Integer.parseInt(lastCid.substring(1)); // Remove "C" prefix
        return String.format("C%03d", idNumber + 1); // Format as "C001", "C002", etc.
    }

    public void updateCareerPortfolio(String studentId, CareerPortfolio portfolio) {
        // Ensure the portfolio is tied to the logged-in student
        portfolio.setSid(studentId);

        // Update the portfolio in the database
        careerPortfolioRepository.update(portfolio);
    }
}
