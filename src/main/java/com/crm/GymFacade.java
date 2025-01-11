package com.crm;

import com.crm.services.TraineeService;
import com.crm.services.TrainerService;
import com.crm.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
}
