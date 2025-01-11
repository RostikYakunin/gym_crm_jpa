package com.crm.repositories.impl;

import com.crm.models.users.Trainer;
import com.crm.repositories.TrainerRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class TrainerRepoImpl implements TrainerRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Trainer> findById(long id) {
        log.debug("Start searching trainer by id... ");
        return Optional.ofNullable(entityManager.find(Trainer.class, id));
    }

    @Override
    public Optional<Trainer> findByUserName(String username) {
        log.debug("Start searching trainer by username... ");
        String query = "SELECT t FROM Trainer t WHERE t.username= :username";
        var trainer = (Trainer) entityManager.createQuery(query)
                .setParameter("username", username)
                .getSingleResult();

        return Optional.ofNullable(trainer);
    }

    @Override
    @Transactional
    public Trainer save(Trainer entity) {
        log.debug("Start saving trainer... ");
        if (entity.getId() != 0) {
            log.debug("Start merging trainer with id= " + entity.getId());
            return entityManager.merge(entity);
        }

        log.debug("Start merging trainer... ");
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Trainer update(Trainer entity) {
        log.debug("Start updating trainer... ");
        return save(entity);
    }

    @Override
    public void delete(Trainer entity) {
        log.debug("Start deleting trainer... ");
        entityManager.remove(entity);
    }

    @Override
    public boolean isExistsById(long id) {
        log.debug("Start searching trainer with id= " + id);
        var query = "SELECT COUNT(t) FROM Trainer t WHERE t.id = :id";
        var count = (Long) entityManager.createQuery(query)
                .setParameter("id", id)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public boolean isUserNameExists(String username) {
        log.debug("Start searching trainer with username= " + username);
        var query = "SELECT COUNT(t) FROM Trainer t WHERE t.username = :username";
        var count = (Long) entityManager.createQuery(query)
                .setParameter("username", username)
                .getSingleResult();

        return count > 0;
    }
}
