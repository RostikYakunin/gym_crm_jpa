package com.crm.repositories.impl;

import com.crm.DbTestBase;
import com.crm.repositories.entities.TrainingType;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerRepoImplTest extends DbTestBase {

    @BeforeEach
    void init() {
        trainerRepo.save(testTrainer);
    }

    @Test
    @DisplayName("Save a trainer and verify it is persisted")
    void saveTrainer_ShouldPersistTrainer() {
        // Given - When
        var savedTrainer = trainerRepo.save(testTrainer);

        // Then
        assertNotNull(savedTrainer.getId());
        assertEquals("testName1.testLastName1", savedTrainer.getUsername());
    }

    @Test
    @DisplayName("Find a trainer by existing ID and verify it is returned")
    void findTrainerById_WhenIdExists_ShouldReturnTrainer() {
        // Given
        trainerRepo.save(testTrainer);

        // When
        var foundTrainer = trainerRepo.findById(testTrainer.getId());

        // Then
        assertTrue(foundTrainer.isPresent());
        assertEquals("testName1.testLastName1", foundTrainer.get().getUsername());
    }

    @Test
    @DisplayName("Find a trainer by non-existing ID and verify empty result")
    void findTrainerById_WhenIdDoesNotExist_ShouldReturnEmptyOptional() {
        // Given - When
        var foundTrainer = trainerRepo.findById(999L);

        // Then
        assertFalse(foundTrainer.isPresent());
    }

    @Test
    @DisplayName("Update a trainer and verify the changes are saved")
    void updateTrainer_ShouldSaveUpdatedTrainer() {
        // Given
        trainerRepo.save(testTrainer);
        testTrainer.setUsername("NewTrainerName");

        // When
        var updatedTrainer = trainerRepo.update(testTrainer);

        // Then
        assertEquals("NewTrainerName", updatedTrainer.getUsername());
    }

    @Test
    @DisplayName("Delete a trainer and verify it is removed")
    void deleteTrainer_ShouldRemoveTrainer() {
        // Given
        trainerRepo.save(testTrainer);

        // When
        trainerRepo.delete(testTrainer);

        // Then
        var deletedTrainer = trainerRepo.findById(testTrainer.getId());
        assertFalse(deletedTrainer.isPresent());
    }

    @Test
    @DisplayName("Check if trainer exists by ID and verify true is returned")
    void existsById_WhenIdExists_ShouldReturnTrue() {
        // Given
        trainerRepo.save(testTrainer);

        // When
        var result = trainerRepo.isExistsById(testTrainer.getId());

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if trainer exists by ID and verify false is returned")
    void existsById_WhenIdDoesNotExist_ShouldReturnFalse() {
        // Given - When
        var result = trainerRepo.isExistsById(999L);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Get trainer trainings by criteria and verify result")
    void getTrainerTrainingsByCriteria_ShouldReturnCorrectTrainings() {
        // Given
        traineeRepo.save(testTrainee);
        trainingRepo.save(testTraining);

        // When
        var trainings = trainerRepo.getTrainerTrainingsByCriteria(
                testTrainer.getUsername(), LocalDate.now(), null, testTrainee.getFirstName(), TrainingType.FITNESS
        );

        // Then
        assertFalse(trainings.isEmpty());
        assertEquals(1, trainings.size());
        assertEquals(testTraining.getTrainingName(), trainings.get(0).getTrainingName());
    }

    @Test
    @DisplayName("Get unassigned trainers by trainee username and verify result")
    void getUnassignedTrainersByTraineeUsername_ShouldReturnCorrectTrainers() {
        // Given
        traineeRepo.save(testTrainee);
        traineeRepo.save(testTrainee);

        // When
        var trainers = trainerRepo.getUnassignedTrainersByTraineeUsername(testTrainee.getUsername());

        // Then
        assertFalse(trainers.isEmpty());
        assertEquals(testTrainer.getUsername(), trainers.get(0).getUsername());
    }

    @Test
    @DisplayName("isUserNameExists - should return true when entity was found")
    void isUserNameExists_ShouldReturnTrue_WhenEntityWasFound() {
        // Given - When
        var result = trainerRepo.isUserNameExists("testName1.testLastName1");

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("isUserNameExists - should return false when entity was not found")
    void isUserNameExists_ShouldReturnFalse_WhenEntityWasNotFound() {
        // Given - When
        var result = trainerRepo.isUserNameExists("unknown1");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("findByUserName - should return entity when it was found")
    void findByUserName_ShouldReturnEntity_WhenEntityWasFound() {
        // Given - When
        var result = trainerRepo.findByUserName("testName1.testLastName1");

        // Then
        assertNotNull(result.get());
        assertEquals(testTrainer, result.get());
    }

    @Test
    @DisplayName("findByUserName - should throw exception when it was not found")
    void findByUserName_ShouldThrowException_WhenEntityWasNotFound() {
        // Given - When - Then
        assertThrows(
                NoResultException.class,
                () -> trainerRepo.findByUserName("unknown")
        );
    }
}