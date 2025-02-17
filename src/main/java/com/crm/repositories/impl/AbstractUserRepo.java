package com.crm.repositories.impl;

import com.crm.repositories.UserRepo;
import com.crm.repositories.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public abstract class AbstractUserRepo<T extends User> implements UserRepo<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<T> findById(long id) {
        log.debug("Start searching entity by id... ");
        return Optional.ofNullable(entityManager.find(getEntityClass(), id));
    }

    @Override
    public Optional<T> findByUserName(String username) {
        log.debug("Start searching entity by username... ");
        var query = "SELECT u FROM " + getEntityClassName() + " u WHERE u.username = :username";
        var user = (T) entityManager.createQuery(query)
                .setParameter("username", username)
                .getSingleResult();
        return Optional.ofNullable(user);
    }

    @Override
    public T save(T entity) {
        log.debug("Start persisting new entity... ");
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        log.debug("Start updating entity... ");
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        log.debug("Start deleting entity... ");
        if (!entityManager.contains(entity)) {
            entity = entityManager.merge(entity);
        }

        entityManager.remove(entity);
    }

    @Override
    public boolean isExistsById(long id) {
        log.debug("Start searching entity with id= " + id);
        var query = "SELECT COUNT(e) FROM " + getEntityClassName() + " e WHERE e.id = :id";
        var count = (Long) entityManager.createQuery(query)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean isUserNameExists(String username) {
        log.debug("Start searching entity with username= " + username);
        var query = "SELECT COUNT(e) FROM " + getEntityClassName() + " e WHERE e.username = :username";
        var count = (Long) entityManager.createQuery(query)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    protected abstract Class<T> getEntityClass();

    protected abstract String getEntityClassName();
}
