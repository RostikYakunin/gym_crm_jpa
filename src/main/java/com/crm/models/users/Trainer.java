package com.crm.models.users;

import com.crm.models.training.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Trainer extends User {
    private long userId;
    private String specialization;
    private TrainingType trainingType;
}
