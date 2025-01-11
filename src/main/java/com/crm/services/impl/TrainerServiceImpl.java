package com.crm.services.impl;

import com.crm.models.training.TrainingType;
import com.crm.models.users.Trainer;
import com.crm.repositories.TrainerRepo;
import com.crm.services.TrainerService;
import com.crm.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepo trainerRepo;

    @Override
    public Trainer findById(long id) {
        log.info("Searching for trainer with id={}", id);
        return trainerRepo.findById(id).orElse(null);
    }

    @Override
    public Trainer findByUsername(String username) {
        log.info("Searching for trainer with username={}", username);
        return trainerRepo.findByUserName(username).orElse(null);
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

    @Override
    public Trainer save(Trainer trainer) {
        log.info("Starting saving trainer with first name: {}", trainer.getFirstName());

        var uniqueUsername = UserUtils.generateUniqueUsername(
                trainer,
                trainerRepo::isUserNameExists
        );
        var generatedPassword = UserUtils.generatePassword();

        trainer.setUsername(uniqueUsername);
        trainer.setPassword(generatedPassword);
        trainer.setIsActive(true);

        var savedTrainer = trainerRepo.save(trainer);
        log.info("Trainer with id={} was successfully saved", savedTrainer.getId());

        return savedTrainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        var trainerId = trainer.getId();
        log.info("Started updating process for trainer with id={}", trainerId);

        boolean existsById = trainerRepo.isExistsById(trainerId);
        if (!existsById) {
            log.error("Trainer with id={} not found, update failed", trainerId);
            throw new NoSuchElementException("Trainer with id=" + trainerId + " not found");
        }

        log.info("Starting updating trainer... ");
        var updatedTrainer = trainerRepo.update(trainer);
        log.info("Trainer with id={} was successfully updated", trainerId);

        return updatedTrainer;
    }

    @Override
    public Trainer changePassword(Trainer trainer, String password) {
        log.info("Starting changing password for trainee... ");

        trainer.setPassword(password);
        var updatedTrainer = update(trainer);

        log.info("Password`s changing successfully completed...");
        return updatedTrainer;
    }

    @Override
    public Trainer toggleActiveStatus(long trainerId) {
        log.info("Starting changing status for trainer with id=" + trainerId);
        var foundTrainer = trainerRepo.findById(trainerId)
                .orElseThrow(
                        () -> {
                            log.error("Trainer with id={} not found, deletion failed", trainerId);
                            throw new NoSuchElementException("Trainer with id=" + trainerId + " not found");
                        }
                );

        log.info("Changing status for trainer with id={} from {} to {}", trainerId, foundTrainer.getIsActive(), !foundTrainer.getIsActive());
        foundTrainer.setIsActive(!foundTrainer.getIsActive());
        var updatedTrainer = trainerRepo.update(foundTrainer);

        log.info("Status changing successfully completed...");
        return updatedTrainer;
    }
}
