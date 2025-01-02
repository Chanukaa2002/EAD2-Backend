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

    @Transactional
    public void save(Badge badge) {
        entityManager.persist(badge);
    }

    public Badge findById(String bid) {
        return entityManager.find(Badge.class, bid);
    }

    public List<Badge> findAll() {
        return entityManager.createQuery("SELECT b FROM Badge b", Badge.class).getResultList();
    }

    @Transactional
    public void deleteById(String bid) {
        Badge badge = findById(bid);
        if (badge != null) {
            entityManager.remove(badge);
        }
    }

    public List<Badge> findBadgesByLecturer(String lecturerId) {
        return entityManager.createQuery(
                        "SELECT b FROM Badge b WHERE b.lecturer.lid = :lecturerId AND b.status = 'Active'", Badge.class)
                .setParameter("lecturerId", lecturerId)
                .getResultList();
    }
}
