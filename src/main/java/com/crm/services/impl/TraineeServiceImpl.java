package com.crm.services.impl;

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

    @Override
    public Trainee findById(long id) {
        log.info("Searching for trainee with id={}", id);
        return traineeRepo.findById(id).orElse(null);
    }

    @Override
    public Trainee findByUsername(String username) {
        log.info("Searching for trainee with username={}", username);
        return traineeRepo.findByUserName(username).orElse(null);
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
        log.info("Started saving trainee with first name: {}", trainee.getFirstName());

        var uniqueUsername = UserUtils.generateUniqueUsername(
                trainee,
                traineeRepo::isUserNameExists
        );
        var generatedPassword = UserUtils.generatePassword();

        trainee.setUsername(uniqueUsername);
        trainee.setPassword(generatedPassword);
        trainee.setActive(true);

        var savedTrainee = traineeRepo.save(trainee);
        log.info("Trainee with id={} was successfully saved", savedTrainee.getId());

        return savedTrainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        var traineeId = trainee.getId();
        log.info("Started updating process for trainee with id={}", traineeId);

        boolean existsById = traineeRepo.isExistsById(traineeId);
        if (!existsById) {
            log.error("Trainee with id={} not found, update failed", traineeId);
            throw new NoSuchElementException("Trainee with id=" + traineeId + " not found");
        }

        log.info("Starting updating trainee... ");
        var updatedTrainee = traineeRepo.update(trainee);
        log.info("Trainee with id={} was successfully updated", traineeId);

        return updatedTrainee;
    }

    @Override
    public void delete(Trainee trainee) {
        var traineeId = trainee.getId();
        log.info("Attempting to delete trainee with id: {}", traineeId);

        if (!traineeRepo.isExistsById(traineeId)) {
            log.error("Trainee with id={} not found, deletion failed", traineeId);
            throw new NoSuchElementException("Trainee with id=" + traineeId + " not found");
        }

        traineeRepo.delete(trainee);
        log.info("Trainee with id={} was successfully deleted", traineeId);
    }

    @Override
    public void deleteByUsername(String username) {
        log.info("Started deleting trainee by username... ");
        var foundTrainee = traineeRepo.findByUserName(username)
                .orElseThrow(
                        () -> {
                            log.error("Trainee with username={} not found, deletion failed", username);
                            throw new NoSuchElementException("Trainee with username=" + username + " not found");
                        }
                );

        traineeRepo.delete(foundTrainee);
        log.info("Trainee with username=" + username + " was successfully deleted");
    }

    @Override
    public Trainee changePassword(Trainee trainee, String password) {
        log.info("Starting changing password for trainee... ");

        trainee.setPassword(password);
        var updatedTrainee = traineeRepo.update(trainee);

        log.info("Password`s changing successfully completed...");
        return updatedTrainee;
    }

    @Override
    public Trainee toggleActiveStatus(long traineeId) {
        log.info("Starting changing status for trainee with id=" + traineeId);
        var foundTrainee = traineeRepo.findById(traineeId)
                .orElseThrow(
                        () -> {
                            log.error("Trainee with id={} not found, deletion failed", traineeId);
                            throw new NoSuchElementException("Trainee with id=" + traineeId + " not found");
                        }
                );

        log.info("Changing status for trainer with id={} from {} to {}", traineeId, foundTrainee.isActive(), !foundTrainee.isActive());
        foundTrainee.setActive(!foundTrainee.isActive());
        var updatedTrainee = traineeRepo.update(foundTrainee);

        log.info("Status changing successfully completed...");
        return updatedTrainee;
    }
}
