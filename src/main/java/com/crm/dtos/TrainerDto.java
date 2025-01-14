package com.crm.dtos;

import com.crm.repositories.entities.Trainee;
import com.crm.repositories.entities.TrainingType;
import com.crm.repositories.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerDto extends UserDto{
    private User user;

    @NotNull(message = "Specialization is mandatory")
    private TrainingType specialization;

    @NotNull(message = "Trainees list cannot be null")
    @Builder.Default
    private Set<Trainee> trainees = new HashSet<>();
}
