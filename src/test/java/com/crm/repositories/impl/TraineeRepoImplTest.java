package com.crm.repositories.impl;

import com.crm.DbTestBase;
import com.crm.models.TrainingType;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeRepoImplTest extends DbTestBase {

    @BeforeEach
    void init() {
        traineeRepo.save(testTrainee);
    }

    @Test
    @DisplayName("Save a trainee and verify it is persisted")
    void saveTrainee_ShouldPersistTrainee() {
        // Given - When
        var savedTrainee = traineeRepo.save(testTrainee);

        // Then
        assertNotNull(savedTrainee.getId());
        assertEquals("testName.testLastName", savedTrainee.getUsername());
    }

    @Test
    @DisplayName("Find a trainee by existing ID and verify it is returned")
    void findTraineeById_WhenIdExists_ShouldReturnTrainee() {
        // Given
        traineeRepo.save(testTrainee);

        // When
        var foundTrainee = traineeRepo.findById(1);

        // Then
        assertTrue(foundTrainee.isPresent());
        assertEquals("testName.testLastName", foundTrainee.get().getUsername());
    }

    @Test
    @DisplayName("Find a trainee by non-existing ID and verify empty result")
    void findTraineeById_WhenIdDoesNotExist_ShouldReturnEmptyOptional() {
        // Given - When
        var foundTrainee = traineeRepo.findById(999L);

        // Then
        assertFalse(foundTrainee.isPresent());
    }

    @Test
    @DisplayName("Update a trainee and verify the changes are saved")
    void updateTrainee_ShouldSaveUpdatedTrainee() {
        // Given
        traineeRepo.save(testTrainee);
        testTrainee.setUsername("NewTraineeName");

        // When
        var updatedTrainee = traineeRepo.update(testTrainee);

        // Then
        assertEquals("NewTraineeName", updatedTrainee.getUsername());
    }

    @Test
    @DisplayName("Delete a trainee and verify it is removed")
    void deleteTrainee_ShouldRemoveTrainee() {
        // Given
        traineeRepo.save(testTrainee);

        // When
        traineeRepo.delete(testTrainee);

        // Then
        var deletedTrainee = traineeRepo.findById(testTrainee.getId());
        assertFalse(deletedTrainee.isPresent());
    }

    @Test
    @DisplayName("Check if trainee exists by ID and verify true is returned")
    void existsById_WhenIdExists_ShouldReturnTrue() {
        // Given
        traineeRepo.save(testTrainee);

        // When
        var result = traineeRepo.isExistsById(testTrainee.getId());

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if trainee exists by ID and verify false is returned")
    void existsById_WhenIdDoesNotExist_ShouldReturnFalse() {
        // Given - When
        var result = traineeRepo.isExistsById(999L);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Get trainee trainings by criteria and verify result")
    void getTraineeTrainingsByCriteria_ShouldReturnCorrectTrainings() {
        // Given
        trainerRepo.save(testTrainer);
        trainingRepo.save(testTraining);

        // When
        var trainings = traineeRepo.getTraineeTrainingsByCriteria(
                testTrainee.getUsername(), LocalDate.now(), null, testTrainer.getFirstName(), TrainingType.FITNESS
        );

        // Then
        assertFalse(trainings.isEmpty());
        assertEquals(1, trainings.size());
        assertEquals(testTraining.getTrainingName(), trainings.get(0).getTrainingName());
    }

    @Test
    @DisplayName("isUserNameExists - should return true when entity was found")
    void isUserNameExists_ShouldReturnTrue_WhenEntityWasFound() {
        // Given - When
        var result = traineeRepo.isUserNameExists("testName.testLastName");

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("isUserNameExists - should return false when entity was not found")
    void isUserNameExists_ShouldReturnFalse_WhenEntityWasNotFound() {
        // Given - When
        var result = traineeRepo.isUserNameExists("unknown");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("findByUserName - should return entity when it was found")
    void findByUserName_ShouldReturnEntity_WhenEntityWasFound() {
        // Given - When
        var result = traineeRepo.findByUserName("testName.testLastName");

        // Then
        assertNotNull(result.get());
        assertEquals(testTrainee, result.get());
    }

    @Test
    @DisplayName("findByUserName - should throw exception when it was not found")
    void findByUserName_ShouldThrowException_WhenEntityWasNotFound() {
        // Given - When - Then
        assertThrows(
                NoResultException.class,
                () -> traineeRepo.findByUserName("unknown")
        );
    }
}