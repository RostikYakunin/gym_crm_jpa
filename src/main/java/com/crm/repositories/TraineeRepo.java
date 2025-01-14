package com.crm.repositories;

import com.crm.repositories.entities.Trainee;
import com.crm.repositories.entities.Trainer;
import com.crm.repositories.entities.Training;
import com.crm.repositories.entities.TrainingType;

import java.time.LocalDate;
import java.util.List;

public interface TraineeRepo extends UserRepo<Trainee> {
    List<Training> getTraineeTrainingsByCriteria(String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerName, TrainingType trainingType);

    void updateTraineeTrainersList(String traineeUsername, List<Trainer> trainers);
}
