package com.crm.mapper;

import com.crm.models.training.TrainingType;
import com.crm.models.users.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TrainerMapperTest {
    private TrainerMapper trainerMapper;
    private Trainer testTrainer;

    @BeforeEach
    void setUp() {
        trainerMapper = Mappers.getMapper(TrainerMapper.class);
        testTrainer = Trainer.builder()
                .firstName("Alice")
                .lastName("Smith")
                .username("Alice.Smith")
                .password("password")
                .specialization("testSpecialization")
                .trainingType(TrainingType.FITNESS)
                .isActive(true)
                .build();
    }

    @Test
    @DisplayName("Should be updated only non null fields when exists updated fields")
    void givenUpdatedTrainer_whenUpdateTrainer_thenOnlyNonNullFieldsShouldBeUpdated() {
        // Given
        var updatedTrainer = Trainer.builder()
                .lastName("Johnson")
                .isActive(false)
                .build();

        // When
        trainerMapper.updateTrainer(testTrainer, updatedTrainer);

        // Then
        assertEquals("Alice", testTrainer.getFirstName());
        assertEquals("Johnson", testTrainer.getLastName());
        assertEquals("Alice.Smith", testTrainer.getUsername());
        assertEquals("password", testTrainer.getPassword());
        assertFalse(testTrainer.isActive());
    }

    @Test
    @DisplayName("Should not update any fields when all fields are null")
    void givenNullUpdatedTrainer_whenUpdateTrainer_thenNoFieldsShouldBeUpdated() {
        // Given
        var updatedTrainer = Trainer.builder().build();

        // When
        trainerMapper.updateTrainer(testTrainer, updatedTrainer);

        System.out.println(testTrainer);

        // Then
        assertEquals("Alice", testTrainer.getFirstName());
        assertEquals("Smith", testTrainer.getLastName());
        assertEquals("Alice.Smith", testTrainer.getUsername());
        assertEquals("password", testTrainer.getPassword());
    }
}