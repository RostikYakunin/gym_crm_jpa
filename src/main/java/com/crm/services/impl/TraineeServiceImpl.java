package com.crm.services.impl;

import com.crm.repositories.TraineeRepo;
import com.crm.repositories.entities.Trainee;
import com.crm.services.TraineeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class TraineeServiceImpl extends AbstractUserService<Trainee, TraineeRepo> implements TraineeService {
    public TraineeServiceImpl(TraineeRepo repository) {
        super(repository);
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

        return super.save(newTrainee);
    }

    @Override
    public void delete(Trainee trainee) {
        var traineeId = trainee.getId();
        log.info("Attempting to delete trainee with id: {}", traineeId);

        if (!repository.isExistsById(traineeId)) {
            log.error("Trainee with id={} not found, deletion failed", traineeId);
            throw new NoSuchElementException("Trainee with id=" + traineeId + " not found");
        }

        repository.delete(trainee);
        log.info("Trainee with id={} was successfully deleted", traineeId);
    }

    @Override
    public void deleteByUsername(String username) {
        log.info("Started deleting trainee by username... ");
        var foundTrainee = repository.findByUserName(username)
                .orElseThrow(
                        () -> {
                            log.error("Trainee with username={} not found, deletion failed", username);
                            throw new NoSuchElementException("Trainee with username=" + username + " not found");
                        }
                );

        repository.delete(foundTrainee);
        log.info("Trainee with username=" + username + " was successfully deleted");
    }
}
