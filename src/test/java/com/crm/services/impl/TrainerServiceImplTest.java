package com.crm.services.impl;

import com.crm.UnitTestBase;
import com.crm.models.TrainingType;
import com.crm.repositories.TrainerRepo;
import com.crm.repositories.entities.Trainer;
import com.crm.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest extends UnitTestBase {
    @Mock
    private TrainerRepo trainerRepo;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    @DisplayName("findById should return trainer when exists")
    void findById_ShouldReturnTrainer_WhenExists() {
        // Given
        when(trainerRepo.findById(anyLong())).thenReturn(Optional.of(testTrainer));

        // When
        var result = trainerService.findById(testTrainer.getId());

        // Then
        assertEquals(testTrainer, result);
        verify(trainerRepo, times(1)).findById(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("save should generate username and password, then save trainer")
    void save_ShouldGenerateUsernameAndPasswordAndSaveTrainer() {
        // Given
        String expectedUserName = "testName1.testLastName1";

        when(trainerRepo.save(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.save(testTrainer);

        // Then
        assertEquals(expectedUserName, testTrainer.getUsername());
        assertNotNull(testTrainer.getPassword());
        assertEquals(testTrainer, result);

        verify(trainerRepo, times(1)).save(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("save should save using firstname and lastname, then save trainer")
    void save_ShouldSaveWhitFirstnameAndLastname() {
        // Given
        String expectedUserName = "testName1.testLastName1";

        when(trainerRepo.save(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.save("testName1", "testLastName1", "password", TrainingType.FITNESS);

        // Then
        assertEquals(expectedUserName, testTrainer.getUsername());
        assertNotNull(testTrainer.getPassword());
        assertEquals(testTrainer, result);

        verify(trainerRepo, times(1)).save(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("update should throw exception when trainer does not exist")
    void update_ShouldThrowException_WhenNotExists() {
        // Given
        when(trainerRepo.isExistsById(anyLong())).thenReturn(false);

        // When & Then
        assertThrows(
                NoSuchElementException.class,
                () -> trainerService.update(testTrainer),
                "Trainer with id=1 not found, updating failed"
        );

        verify(trainerRepo, times(1)).isExistsById(idArgumentCaptor.capture());
        verify(trainerRepo, never()).update(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("update should update trainer if exists")
    void update_ShouldUpdateTrainer_IfExists() {
        // Given
        when(trainerRepo.isExistsById(anyLong())).thenReturn(true);
        when(trainerRepo.update(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.update(testTrainer);

        // Then
        assertEquals(testTrainer, result);
        verify(trainerRepo, times(1)).isExistsById(idArgumentCaptor.capture());
        verify(trainerRepo, times(1)).update(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("findByUsername - should find entity when traineк was found in DB")
    void findByUsername_ShouldFindEntity_WhenTrainerWasFound() {
        // Given
        when(trainerRepo.findByUserName(anyString())).thenReturn(Optional.of(testTrainer));

        // When
        var result = trainerService.findByUsername(testTrainee.getUsername());

        // Then
        assertNotNull(result);
        assertEquals(testTrainer, result);

        verify(trainerRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }

    @Test
    @DisplayName("findByUsername - should not find entity when trainer was not found in DB")
    void findByUsername_ShouldNotFindEntity_WhenTrainerWasNotFound() {
        // Given
        when(trainerRepo.findByUserName(anyString())).thenReturn(Optional.empty());

        // When
        var result = trainerService.findByUsername(testTrainer.getUsername());

        // Then
        assertNull(result);

        verify(trainerRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }

    @Test
    @DisplayName("changePassword - should change password when trainer`s password matches with found in DB")
    void changePassword_ShouldChangePass_WhenPasswordsMatches() {
        // Given
        testTrainer.setPassword(UserUtils.hashPassword(testTrainer.getPassword()));
        when(trainerRepo.update(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.changePassword(testTrainer, "testPassword1", "newPass");

        // Then
        assertTrue(result);
        verify(trainerRepo, times(1)).update(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("changePassword - should change password when trainer`s password matches with found in DB")
    void changePassword_ShouldNotChangePass_WhenPasswordsNotMatches() {
        // Given
        testTrainer.setPassword(UserUtils.hashPassword(testTrainer.getPassword()));
        when(trainerRepo.update(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.changePassword(testTrainer, testTrainer.getPassword(), "newPass");

        // Then
        assertFalse(result);
        verify(trainerRepo, never()).update(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Toggle active status - should deactivate when currently active")
    void toggleActiveStatus_ShouldDeactivateWhenCurrentlyActive() {
        // Given
        when(trainerRepo.findById(1L)).thenReturn(Optional.of(testTrainer));
        when(trainerRepo.update(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.toggleActiveStatus(1L);

        // Then
        assertFalse(result);
        assertFalse(testTrainer.isActive());

        verify(trainerRepo, times(1)).findById(1L);
        verify(trainerRepo, times(1)).update(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Toggle active status - should activate when currently inactive")
    void toggleActiveStatus_ShouldActivateWhenCurrentlyInactive() {
        // Given
        testTrainer.setActive(false);
        when(trainerRepo.findById(1L)).thenReturn(Optional.of(testTrainer));
        when(trainerRepo.update(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.toggleActiveStatus(1L);

        // Then
        assertTrue(result);
        assertTrue(testTrainer.isActive());

        verify(trainerRepo, times(1)).findById(1L);
        verify(trainerRepo, times(1)).update(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Toggle active status - should throw exception when entity not found")
    void toggleActiveStatus_WhenEntityNotFound_ShouldThrowException() {
        // Given
        when(trainerRepo.findById(999L)).thenReturn(Optional.empty());

        // When - Then
        assertThrows(
                NoSuchElementException.class,
                () -> trainerService.toggleActiveStatus(999L),
                "Entity with id=999 not found"
        );

        verify(trainerRepo, times(1)).findById(999L);
        verify(trainerRepo, never()).update(any(Trainer.class));
    }

    @Test
    @DisplayName("Is username and password matching - should return true for matching credentials")
    void isUsernameAndPasswordMatching_ShouldReturnTrueForMatchingCredentials() {
        // Given
        testTrainer.setPassword(UserUtils.hashPassword(testTrainer.getPassword()));
        when(trainerRepo.findByUserName(anyString())).thenReturn(Optional.of(testTrainer));

        // When
        var result = trainerService.isUsernameAndPasswordMatching("testName1.testLastName1", "testPassword1");

        // Then
        assertTrue(result);
        verify(trainerRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Is username and password matching - should return false for non-matching credentials")
    void isUsernameAndPasswordMatching_ShouldReturnFalseForNonMatchingCredentials() {
        // Given
        testTrainer.setPassword(UserUtils.hashPassword(testTrainee.getPassword()));
        when(trainerRepo.findByUserName(anyString())).thenReturn(Optional.of(testTrainer));

        // When
        var result = trainerService.isUsernameAndPasswordMatching("testName1.testLastName1", "wrongPassword");

        // Then
        assertFalse(result);
        verify(trainerRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Is username and password matching - should return false when user not found")
    void isUsernameAndPasswordMatching_WhenUserNotFound_ShouldReturnFalse() {
        // Given
        when(trainerRepo.findByUserName(anyString())).thenReturn(Optional.empty());

        // When
        var result = trainerService.isUsernameAndPasswordMatching("unknownUser", "testPassword");

        // Then
        assertFalse(result);
        verify(trainerRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }
}