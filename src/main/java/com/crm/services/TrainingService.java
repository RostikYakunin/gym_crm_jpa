package com.crm.services;

import com.crm.models.training.Training;

public interface TrainingService {
    Training findById(long id);

    Training save(Training training);
}
