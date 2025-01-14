package com.crm.services;

import com.crm.repositories.entities.Trainer;
import com.crm.repositories.entities.TrainingType;

public interface TrainerService extends UserService<Trainer> {
    Trainer save(String firstName, String lastName, TrainingType specialization);
}
