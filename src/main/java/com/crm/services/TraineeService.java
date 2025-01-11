package com.crm.services;

import com.crm.models.users.Trainee;

import java.time.LocalDate;

public interface TraineeService {
    Trainee findById(long id);

    Trainee save(String firstName, String lastName, String address, LocalDate dateOfBirth);

    Trainee save(Trainee trainee);

    Trainee update(Trainee trainee);

    void delete(Trainee trainee);

}
