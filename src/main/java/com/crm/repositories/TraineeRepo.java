package com.crm.repositories;

import com.crm.models.users.Trainee;

import java.util.Optional;

public interface TraineeRepo extends UserRepo<Trainee> {
    Optional<Trainee> findByUserName(String username);
}
