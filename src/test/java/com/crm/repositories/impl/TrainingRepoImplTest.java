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
        var savedTrainee = trainingRepo.save(testTraining);

        // When
        var foundTraining = trainingRepo.findById(savedTrainee.getId());
        var unFoundTraining = trainingRepo.findById(999L);

        // Then
        assertTrue(foundTraining.isPresent());
        assertEquals(savedTrainee.getTrainingName(), foundTraining.get().getTrainingName());
        assertFalse(unFoundTraining.isPresent());
    }

    @Test
    @DisplayName("Update a training and verify the changes are saved")
    void updateTraining_ShouldSaveUpdatedTraining() {
        // Given
        var newTrainingName = "newTrainingName";
        var savedTraining = trainingRepo.save(testTraining);
        testTraining.setTrainingName(newTrainingName);

        // When
        var updatedTraining = trainingRepo.update(testTraining);

        // Then
        assertEquals(newTrainingName, updatedTraining.getTrainingName());
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
    @DisplayName("Check if training exists by ID and verify it is returned")
    void existsById_WhenIdExists_ShouldReturnTrue() {
        // Given
        var savedTraining = trainingRepo.save(testTraining);

        // When
        var result1 = trainingRepo.isExistsById(savedTraining.getId());
        var result2 = trainingRepo.isExistsById(999L);

        // Then
        assertTrue(result1);
        assertFalse(result2);
    }
}