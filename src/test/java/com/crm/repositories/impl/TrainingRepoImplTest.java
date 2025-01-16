package com.crm.repositories.impl;

import com.crm.DbTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingRepoImplTest extends DbTestBase {
    @BeforeEach
    void init() {
        traineeRepo.save(testTrainee);
        trainerRepo.save(testTrainer);
    }

    @Test
    @DisplayName("Save a training and verify it is persisted")
    void saveTraining_ShouldPersistTraining() {
        // Given - When
        var savedTraining = trainingRepo.save(testTraining);

        // Then
        assertNotNull(savedTraining.getId());
        assertEquals("TestName", savedTraining.getTrainingName());
    }

    @Test
    @DisplayName("Find a training by existing ID and verify it is returned")
    void findTrainingById_WhenIdExists_ShouldReturnTraining() {
        // Given
        trainingRepo.save(testTraining);

        // When
        var foundTraining = trainingRepo.findById(1L);

        // Then
        assertTrue(foundTraining.isPresent());
        assertEquals("TestName", foundTraining.get().getTrainingName());
    }

    @Test
    @DisplayName("Find a training by non-existing ID and verify empty result")
    void findTrainingById_WhenIdDoesNotExist_ShouldReturnEmptyOptional() {
        // Given - When
        var foundTraining = trainingRepo.findById(999L);

        // Then
        assertFalse(foundTraining.isPresent());
    }

    @Test
    @DisplayName("Update a training and verify the changes are saved")
    void updateTraining_ShouldSaveUpdatedTraining() {
        // Given
        trainingRepo.save(testTraining);
        testTraining.setTrainingName("newTrainingName");

        // When
        var updatedTraining = trainingRepo.update(testTraining);

        // Then
        assertEquals("newTrainingName", updatedTraining.getTrainingName());
    }

    @Test
    @DisplayName("Delete a training and verify it is removed")
    void deleteTraining_ShouldRemoveTraining() {
        // Given
        trainingRepo.save(testTraining);

        // When
        trainingRepo.delete(testTraining);

        // Then
        var deletedTraining = trainingRepo.findById(1L);
        assertFalse(deletedTraining.isPresent());
    }

    @Test
    @DisplayName("Check if training exists by ID and verify true is returned")
    void existsById_WhenIdExists_ShouldReturnTrue() {
        // Given
        trainingRepo.save(testTraining);

        // When
        var result = trainingRepo.isExistsById(1L);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if training exists by ID and verify false is returned")
    void existsById_WhenIdDoesNotExist_ShouldReturnFalse() {
        // Given - When
        var result = trainingRepo.isExistsById(999L);

        // Then
        assertFalse(result);
    }
}