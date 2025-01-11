package com.crm.repositories.impl;

import com.crm.models.users.Trainee;
import com.crm.models.users.User;
import com.crm.repositories.TraineeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TraineeRepoImpl implements TraineeRepo {
    private static long idCounter = 1;
    private final Map<Long, Trainee> traineeDataBase;

    @Override
    public Optional<Trainee> findById(long id) {
        log.debug("Trying to find trainee by id=" + id);
        return Optional.ofNullable(traineeDataBase.get(id));
    }

    @Override
    public Trainee save(Trainee trainee) {
        trainee.setUserId(generateId());
        log.debug("Trying to save trainee with id=" + trainee.getUserId());

        traineeDataBase.put(trainee.getUserId(), trainee);
        return trainee;
    }

    private long generateId() {
        return idCounter++;
    }

    @Override
    public Trainee update(Trainee trainee) {
        log.debug("Trying to update trainee with id=" + trainee.getUserId());
        return traineeDataBase.put(trainee.getUserId(), trainee);
    }

    @Override
    public void delete(Trainee trainee) {
        var traineeUserId = trainee.getUserId();
        log.debug("Trying to remove trainee with id=" + traineeUserId);

        traineeDataBase.remove(traineeUserId);
        log.debug("Trainee with id=" + traineeUserId + " was successfully removed");
    }

    @Override
    public boolean isExistsById(long id) {
        log.debug("Trying to check if exists trainee with id=" + id);
        return traineeDataBase.containsKey(id);
    }

    @Override
    public boolean isUserNameExists(String username) {
        log.debug("Trying to check if trainee with username=" + username + " is existed");
        return traineeDataBase.values()
                .stream()
                .map(User::getUsername)
                .anyMatch(un -> un.equals(username));
    }
}
