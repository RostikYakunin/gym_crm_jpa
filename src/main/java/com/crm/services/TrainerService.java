package com.crm.services;

import com.crm.models.training.TrainingType;
import com.crm.models.users.Trainer;

public interface TrainerService {
    Trainer findById(long id);

    Trainer findByUsername(String username);

    Trainer save(String firstName, String lastName, TrainingType specialization);

    Trainer save(Trainer trainer);

    Trainer update(Trainer trainer);

    Trainer changePassword(Trainer trainer, String password);

    Trainer toggleActiveStatus(long trainerId);
}
