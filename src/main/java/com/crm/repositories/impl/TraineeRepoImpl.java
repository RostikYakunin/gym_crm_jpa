package com.crm.repositories.impl;

import com.crm.models.users.Trainee;
import com.crm.repositories.TraineeRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class TraineeRepoImpl implements TraineeRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Trainee> findById(long id) {
        log.debug("Start searching trainee by id... ");
        return Optional.ofNullable(entityManager.find(Trainee.class, id));
    }

    @Override
    public Optional<Trainee> findByUserName(String username) {
        log.debug("Start searching trainee by username... ");
        String query = "SELECT t FROM Trainee t WHERE t.username= :username";
        var trainee = (Trainee) entityManager.createQuery(query)
                .setParameter("username", username)
                .getSingleResult();

        return Optional.ofNullable(trainee);
    }

    @Override
    @Transactional
    public Trainee save(Trainee entity) {
        log.debug("Start saving trainee... ");
        if (entity.getId() != 0) {
            log.debug("Start merging training with id= " + entity.getId());
            return entityManager.merge(entity);
        }

        log.debug("Start saving new trainee... ");
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Trainee update(Trainee entity) {
        log.debug("Start updating trainee... ");
        return save(entity);
    }

    @Override
    public void delete(Trainee entity) {
        log.debug("Start deleting trainee... ");
        entityManager.remove(entity);
    }

    @Override
    public boolean isExistsById(long id) {
        log.debug("Start searching trainee with id= " + id);
        var query = "SELECT COUNT(t) FROM Trainee t WHERE t.id = :id";
        var count = (Long) entityManager.createQuery(query)
                .setParameter("id", id)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public boolean isUserNameExists(String username) {
        log.debug("Start searching trainee with username= " + username);
        var query = "SELECT COUNT(t) FROM Trainee t WHERE t.username = :username";
        var count = (Long) entityManager.createQuery(query)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }
}
