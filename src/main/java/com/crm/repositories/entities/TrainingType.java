package com.crm.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum TrainingType {
    FITNESS(1, "Fitness"),
    YOGA(2, "Yoga"),
    ZUMBA(3, "Zumba"),
    STRETCHING(4, "Stretching"),
    RESISTANCE(5, "Resistance");

    private final long id;
    private final String name;
}
