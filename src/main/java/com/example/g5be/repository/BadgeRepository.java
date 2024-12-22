package com.example.g5be.repository;


import com.example.g5be.model.Badge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class BadgeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Save a Badge
    @Transactional
    public void save(Badge badge) {
        entityManager.persist(badge);
    }

    // Find a Badge by ID
    public Badge findById(String bid) {
        return entityManager.find(Badge.class, bid);
    }

    // Find All Badges
    public List<Badge> findAll() {
        return entityManager.createQuery("SELECT b FROM Badge b", Badge.class).getResultList();
    }

    // Delete a Badge by ID
    @Transactional
    public void deleteById(String bid) {
        Badge badge = findById(bid);
        if (badge != null) {
            entityManager.remove(badge);
        }
    }
}
