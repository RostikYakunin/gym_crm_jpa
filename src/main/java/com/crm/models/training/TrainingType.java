package com.crm.models.training;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

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

    public static TrainingType getById(long id) {
        return Arrays.stream(values())
                .filter(type -> type.id == id)
                .findFirst()
                .orElseThrow(
                        () -> {
                            log.error("Cannot find TrainingType with id=" + id);
                            return new IllegalArgumentException("Unknown id: " + id);
                        }
                );
    }
}
