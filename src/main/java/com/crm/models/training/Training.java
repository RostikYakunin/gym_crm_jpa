package com.crm.models.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Training {
    private long id;
    private long traineeId;
    private long trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDateTime trainingDate;
    private Duration trainingDuration;
}
