package com.crm.repositories.impl;

import com.crm.DbTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingRepoImplTest extends DbTestBase {
    @BeforeEach
    void init() {
        traineeRepo.save(testTrainee);
        trainerRepo.save(testTrainer);
    }

    @Test
    void givenTraining_whenSave_thenTrainingIsPersisted() {
        // Given - When
        var savedTraining = trainingRepo.save(testTraining);

        // Then
        assertNotNull(savedTraining.getId());
        assertEquals("TestName", savedTraining.getTrainingName());
    }

    @Test
    void givenExistingTrainingId_whenFindById_thenTrainingIsReturned() {
        // Given
        trainingRepo.save(testTraining);

        // When
        var foundTraining = trainingRepo.findById(1L);

        // Then
        assertTrue(foundTraining.isPresent());
        assertEquals("TestName", foundTraining.get().getTrainingName());
    }

    @Test
    void givenNonExistingTrainingId_whenFindById_thenEmptyOptionalIsReturned() {
        // Given - When
        var foundTraining = trainingRepo.findById(999L);

        // Then
        assertFalse(foundTraining.isPresent());
    }

    @Test
    void givenTraining_whenUpdate_thenTrainingIsUpdated() {
        // Given
        trainingRepo.save(testTraining);
        testTraining.setTrainingName("newTrainingName");

        // When
        var updatedTraining = trainingRepo.update(testTraining);

        // Then
        assertEquals("newTrainingName", updatedTraining.getTrainingName());
    }

    @Test
    void givenTraining_whenDelete_thenTrainingIsRemoved() {
        // Given
        trainingRepo.save(testTraining);

        // When
        trainingRepo.delete(testTraining);

        // Then
        var deletedTraining = trainingRepo.findById(1L);
        assertFalse(deletedTraining.isPresent());
    }

    @Test
    void givenExistingTrainingId_whenIsExistsById_thenReturnsTrue() {
        // Given
        trainingRepo.save(testTraining);

        // When
        var result = trainingRepo.isExistsById(1L);

        // Then
        assertTrue(result);
    }

    @Test
    void givenNonExistingTrainingId_whenIsExistsById_thenReturnsFalse() {
        // Given - When
        var result = trainingRepo.isExistsById(999L);

        // Then
        assertFalse(result);
    }
}