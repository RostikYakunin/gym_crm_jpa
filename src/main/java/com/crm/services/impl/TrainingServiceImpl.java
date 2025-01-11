package com.crm.services.impl;

import com.crm.models.training.Training;
import com.crm.repositories.TrainingRepo;
import com.crm.services.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepo trainingRepo;

    @Override
    public Training findById(long id) {
        log.info("Searching for trainee with id={}", id);
        return trainingRepo.findById(id).orElse(null);
    }

    @Override
    public Training save(Training training) {
        log.info("Saving training: {}", training);

        var savedTraining = trainingRepo.save(training);
        log.info("Training with id={} was successfully saved", savedTraining.getId());

        return savedTraining;
    }
}
