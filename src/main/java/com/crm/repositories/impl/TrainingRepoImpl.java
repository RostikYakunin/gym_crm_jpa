package com.crm.repositories.impl;

import com.crm.models.training.Training;
import com.crm.repositories.TrainingRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TrainingRepoImpl implements TrainingRepo {
    private static long idCounter = 1;
    private final Map<Long, Training> trainingDataBase;

    @Override
    public Optional<Training> findById(long id) {
        log.debug("Trying to find training by id=" + id);
        return Optional.ofNullable(trainingDataBase.get(id));
    }

    @Override
    public Training save(Training training) {
        training.setId(generateId());
        log.debug("Trying to save training with id=" + training.getId());

        trainingDataBase.put(training.getId(), training);
        return training;
    }

    private long generateId() {
        return idCounter++;
    }

    @Override
    public Training update(Training training) {
        log.error("Method update is not implemented yet... ");
        throw new NotImplementedException("Method update is not implemented yet... ");
    }

    @Override
    public void delete(Training training) {
        log.error("Method delete is not implemented yet... ");
        throw new NotImplementedException("Method delete is not implemented yet... ");
    }

    @Override
    public boolean isExistsById(long id) {
        log.debug("Trying to check if exists training with id=" + id);
        return trainingDataBase.containsKey(id);
    }
}
