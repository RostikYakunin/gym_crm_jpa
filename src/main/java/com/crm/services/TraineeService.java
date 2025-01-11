package com.crm.services;

import com.crm.models.users.Trainee;

import java.time.LocalDate;

public interface TraineeService {
    Trainee findById(long id);

    Trainee findByUsername(String username);

    Trainee save(String firstName, String lastName, String address, LocalDate dateOfBirth);

    Trainee save(Trainee trainee);

    Trainee update(Trainee trainee);

    void delete(Trainee trainee);

    void deleteByUsername(String username);

    Trainee changePassword(Trainee trainee, String password);

    Trainee toggleActiveStatus(long traineeId);
}
