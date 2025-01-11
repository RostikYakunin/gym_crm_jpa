package com.crm.services.impl;

import com.crm.mapper.TraineeMapper;
import com.crm.models.users.Trainee;
import com.crm.repositories.TraineeRepo;
import com.crm.services.TraineeService;
import com.crm.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepo traineeRepo;
    private final TraineeMapper traineeMapper;

    @Override
    public Trainee findById(long id) {
        log.info("Searching for trainee with id={}", id);
        return traineeRepo.findById(id).orElse(null);
    }

    @Override
    public Trainee save(String firstName, String lastName, String address, LocalDate dateOfBirth) {
        log.info("Starting saving trainee using first and last names... ");

        var newTrainee = Trainee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .dateOfBirth(dateOfBirth)
                .build();

        return save(newTrainee);
    }

    @Override
    public Trainee save(Trainee trainee) {
        log.info("Saving trainee: {}", trainee.getFirstName());

        var uniqueUsername = UserUtils.generateUniqueUsername(
                trainee,
                traineeRepo::isUserNameExists
        );
        var generatedPassword = UserUtils.generatePassword();

        trainee.setUsername(uniqueUsername);
        trainee.setPassword(generatedPassword);
        trainee.setActive(true);

        var savedTrainee = traineeRepo.save(trainee);
        log.info("Trainee with id={} was successfully saved", savedTrainee.getUserId());

        return savedTrainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        var traineeId = trainee.getUserId();
        log.info("Starting update process for trainee with id={}", traineeId);

        var existingTrainee = traineeRepo.findById(traineeId)
                .orElseThrow(() -> {
                    log.error("Trainee with id={} not found, update failed", traineeId);
                    return new NoSuchElementException("Trainee with id=" + traineeId + " not found");
                });

        log.info("Starting updating trainee... ");
        traineeMapper.updateTrainee(existingTrainee, trainee);

        var updatedTrainee = traineeRepo.update(existingTrainee);
        log.info("Trainee with id={} was successfully updated", traineeId);

        return updatedTrainee;
    }

    @Override
    public void delete(Trainee trainee) {
        var traineeId = trainee.getUserId();
        log.info("Attempting to delete trainee with id: {}", traineeId);

        if (!traineeRepo.isExistsById(traineeId)) {
            log.error("Trainee with id={} not found, deletion failed", traineeId);
            throw new NoSuchElementException("Trainee with id=" + traineeId + " not found");
        }

        traineeRepo.delete(trainee);
        log.info("Trainee with id={} was successfully deleted", traineeId);
    }
}
