package com.crm.init;

import com.crm.config.AppConfig;
import com.crm.models.training.Training;
import com.crm.models.users.Trainee;
import com.crm.models.users.Trainer;
import com.crm.services.TraineeService;
import com.crm.services.TrainerService;
import com.crm.services.TrainingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@Import(AppConfig.class)
@Slf4j
public class DataInitializer {
    @Value("${data.file.trainee_data}")
    private String traineeDataFilePath;

    @Value("${data.file.trainer_data}")
    private String trainerDataFilePath;

    @Value("${data.file.training_data}")
    private String trainingDataFilePath;

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void initializeData() {
        try {
            log.info("TraineeDataBase`s initialization started ...");
            initializeTraineeData();
            log.info("TraineeDataBase`s initialization successfully completed");

            log.info("TrainerDataBase`s initialization started ...");
            initializeTrainerData();
            log.info("TrainerDataBase`s initialization successfully completed");

            log.info("TrainingDataBase`s initialization started ...");
            initializeTrainingData();
            log.info("TrainingDataBase`s initialization successfully completed");
        } catch (Exception e) {
            log.error("DataBase initialization failed ...");
            throw new RuntimeException("Something went wrong with file deserialization", e);
        }
    }

    private void initializeTraineeData() throws Exception {
        List<Trainee> trainees = objectMapper.readValue(new File(traineeDataFilePath), new TypeReference<>() {
        });
        trainees.forEach(traineeService::save);
    }

    private void initializeTrainerData() throws Exception {
        List<Trainer> trainers = objectMapper.readValue(new File(trainerDataFilePath), new TypeReference<>() {
        });
        trainers.forEach(trainerService::save);
    }

    private void initializeTrainingData() throws Exception {
        List<Training> trainings = objectMapper.readValue(new File(trainingDataFilePath), new TypeReference<>() {
        });
        trainings.forEach(trainingService::save);
    }
}
