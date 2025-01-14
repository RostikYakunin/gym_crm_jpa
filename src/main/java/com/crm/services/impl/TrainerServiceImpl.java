package com.crm.services.impl;

import com.crm.repositories.entities.TrainingType;
import com.crm.repositories.entities.Trainer;
import com.crm.repositories.TrainerRepo;
import com.crm.services.TrainerService;
import com.crm.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class TrainerServiceImpl extends AbstractUserService<Trainer, TrainerRepo> implements TrainerService {
    public TrainerServiceImpl(TrainerRepo repository) {
        super(repository);
    }

    @Override
    public Trainer save(String firstName, String lastName, TrainingType specialization) {
        log.info("Starting saving trainer using first and last names... ");

        var newTrainer = Trainer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .specialization(specialization)
                .build();

        return save(newTrainer);
    }
}
