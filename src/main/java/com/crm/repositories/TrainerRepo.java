package com.crm.repositories;

import com.crm.models.users.Trainer;

import java.util.Optional;

public interface TrainerRepo extends UserRepo<Trainer> {
    Optional<Trainer> findByUserName(String username);
}
