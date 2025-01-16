package com.crm.services.impl;

import com.crm.UnitTestBase;
import com.crm.repositories.TraineeRepo;
import com.crm.repositories.entities.Trainee;
import com.crm.utils.UserUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceImplTest extends UnitTestBase {
    @Mock
    private TraineeRepo traineeRepo;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    @DisplayName("findById should return trainee when exists")
    void findById_ShouldReturnTrainee_WhenExists() {
        // Given
        when(traineeRepo.findById(anyLong())).thenReturn(Optional.of(testTrainee));

        // When
        var result = traineeService.findById(1);

        // Then
        assertEquals(testTrainee, result);
        verify(traineeRepo, times(1)).findById(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("save - should save trainee using parameters")
    void save_ShouldSaveTraineeUsingParameters() {
        // Given
        var expectedUserName = "testName.testLastName";

        when(traineeRepo.save(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.save("John", "Doe", "password", "testAddress", LocalDate.of(1998, 11, 11));

        // Then
        assertEquals(expectedUserName, result.getUsername());
        assertNotNull(result.getPassword());
        assertEquals(result, result);

        verify(traineeRepo, times(1)).save(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("save - should generate username and password, then save trainee")
    void save_ShouldGenerateUsernameAndPasswordAndSaveTrainee() {
        // Given
        var expectedUserName = "testName.testLastName";

        when(traineeRepo.save(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.save(testTrainee);

        // Then
        assertEquals(expectedUserName, result.getUsername());
        assertNotNull(result.getPassword());
        assertEquals(result, result);

        verify(traineeRepo, times(1)).save(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("deleteById should return true when trainee was successfully deleted")
    void deleteById_ShouldDelete_WhenTraineeExists() {
        // Given
        when(traineeRepo.isExistsById(anyLong())).thenReturn(true);
        doNothing().when(traineeRepo).delete(any(Trainee.class));

        // When
        traineeService.delete(testTrainee);

        // Then
        verify(traineeRepo, times(1)).delete(traineeArgumentCaptor.capture());
        verify(traineeRepo, times(1)).isExistsById(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("deleteById should throw Exception when trainee was not found in DB")
    void deleteById_ShouldTrowException_WhenTraineeWasNotFound() {
        // Given
        when(traineeRepo.isExistsById(anyLong())).thenReturn(false);

        // When - Then
        assertThrows(
                NoSuchElementException.class,
                () -> traineeService.delete(testTrainee),
                "Trainee with id=1 not found"
        );

        verify(traineeRepo, never()).delete(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("update should return updated trainee when trainee exists")
    void update_ShouldReturnUpdatedTrainee_WhenTraineeExists() {
        // Given
        when(traineeRepo.isExistsById(anyLong())).thenReturn(true);
        when(traineeRepo.update(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.update(testTrainee);

        // Then
        assertNotNull(result);
        assertEquals(testTrainee, result);

        verify(traineeRepo, times(1)).update(traineeArgumentCaptor.capture());
        verify(traineeRepo, times(1)).isExistsById(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("update should throw Exception when trainee was not found")
    void update_ShouldThrowException_WhenTraineeWasNotFound() {
        // Given
        when(traineeRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(traineeRepo.isExistsById(anyLong())).thenReturn(false);

        // When - Then
        assertThrows(
                NoSuchElementException.class,
                () -> traineeService.update(testTrainee),
                "Trainee with id=1 not found"
        );

        verify(traineeRepo, times(1)).isExistsById(idArgumentCaptor.capture());
        verify(traineeRepo, never()).update(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("deleteByUserName - should throw Exception when trainee was not found in DB")
    void deleteByUserName_ShouldTrowException_WhenTraineeWasNotFound() {
        // Given
        when(traineeRepo.findByUserName(anyString())).thenReturn(Optional.empty());

        // When - Then
        assertThrows(
                NoSuchElementException.class,
                () -> traineeService.deleteByUsername(testTrainee.getUsername()),
                "Trainee with id=1 not found"
        );

        verify(traineeRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
        verify(traineeRepo, never()).delete(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("deleteByUserName - should delete entity when trainee was found in DB")
    void deleteByUserName_ShouldDeleteEntity_WhenTraineeWasFound() {
        // Given
        when(traineeRepo.findByUserName(anyString())).thenReturn(Optional.of(testTrainee));
        doNothing().when(traineeRepo).delete(any(Trainee.class));

        // When
        traineeService.deleteByUsername(testTrainee.getUsername());

        // Then
        verify(traineeRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
        verify(traineeRepo, times(1)).delete(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("findByUsername - should find entity when trainee was found in DB")
    void findByUsername_ShouldFindEntity_WhenTraineeWasFound() {
        // Given
        when(traineeRepo.findByUserName(anyString())).thenReturn(Optional.of(testTrainee));

        // When
        var result = traineeService.findByUsername(testTrainee.getUsername());

        // Then
        assertNotNull(result);
        assertEquals(testTrainee, result);

        verify(traineeRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }

    @Test
    @DisplayName("findByUsername - should not find entity when trainee was not found in DB")
    void findByUsername_ShouldNotFindEntity_WhenTraineeWasNotFound() {
        // Given
        when(traineeRepo.findByUserName(anyString())).thenReturn(Optional.empty());

        // When
        var result = traineeService.findByUsername(testTrainee.getUsername());

        // Then
        assertNull(result);

        verify(traineeRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }

    @Test
    @DisplayName("changePassword - should change password when trainee`s password matches with found in DB")
    void changePassword_ShouldChangePass_WhenPasswordsMatches() {
        // Given
        testTrainee.setPassword(UserUtils.hashPassword(testTrainee.getPassword()));
        when(traineeRepo.update(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.changePassword(testTrainee, "testPassword", "newPass");

        // Then
        assertTrue(result);
        verify(traineeRepo, times(1)).update(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("changePassword - should change password when trainee`s password matches with found in DB")
    void changePassword_ShouldNotChangePass_WhenPasswordsNotMatches() {
        // Given
        testTrainee.setPassword(UserUtils.hashPassword(testTrainee.getPassword()));
        when(traineeRepo.update(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.changePassword(testTrainee, testTrainee.getPassword(), "newPass");

        // Then
        assertFalse(result);
        verify(traineeRepo, never()).update(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Toggle active status - should deactivate when currently active")
    void toggleActiveStatus_ShouldDeactivateWhenCurrentlyActive() {
        // Given
        when(traineeRepo.findById(1L)).thenReturn(Optional.of(testTrainee));
        when(traineeRepo.update(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.toggleActiveStatus(1L);

        // Then
        assertFalse(result);
        assertFalse(testTrainee.isActive());

        verify(traineeRepo, times(1)).findById(1L);
        verify(traineeRepo, times(1)).update(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Toggle active status - should activate when currently inactive")
    void toggleActiveStatus_ShouldActivateWhenCurrentlyInactive() {
        // Given
        testTrainee.setActive(false);
        when(traineeRepo.findById(1L)).thenReturn(Optional.of(testTrainee));
        when(traineeRepo.update(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.toggleActiveStatus(1L);

        // Then
        assertTrue(result);
        assertTrue(testTrainee.isActive());

        verify(traineeRepo, times(1)).findById(1L);
        verify(traineeRepo, times(1)).update(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Toggle active status - should throw exception when entity not found")
    void toggleActiveStatus_WhenEntityNotFound_ShouldThrowException() {
        // Given
        when(traineeRepo.findById(999L)).thenReturn(Optional.empty());

        // When - Then
        assertThrows(
                NoSuchElementException.class,
                () -> traineeService.toggleActiveStatus(999L),
                "Entity with id=999 not found"
        );

        verify(traineeRepo).findById(999L);
        verify(traineeRepo, never()).update(any(Trainee.class));
    }

    @Test
    @DisplayName("Is username and password matching - should return true for matching credentials")
    void isUsernameAndPasswordMatching_ShouldReturnTrueForMatchingCredentials() {
        // Given
        testTrainee.setPassword(UserUtils.hashPassword(testTrainee.getPassword()));
        when(traineeRepo.findByUserName(anyString())).thenReturn(Optional.of(testTrainee));

        // When
        var result = traineeService.isUsernameAndPasswordMatching("testName.testLastName", "testPassword");

        // Then
        assertTrue(result);
        verify(traineeRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Is username and password matching - should return false for non-matching credentials")
    void isUsernameAndPasswordMatching_ShouldReturnFalseForNonMatchingCredentials() {
        // Given
        testTrainee.setPassword(UserUtils.hashPassword(testTrainee.getPassword()));
        when(traineeRepo.findByUserName(anyString())).thenReturn(Optional.of(testTrainee));

        // When
        var result = traineeService.isUsernameAndPasswordMatching("testName.testLastName", "wrongPassword");

        // Then
        assertFalse(result);
        verify(traineeRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Is username and password matching - should return false when user not found")
    void isUsernameAndPasswordMatching_WhenUserNotFound_ShouldReturnFalse() {
        // Given
        when(traineeRepo.findByUserName(anyString())).thenReturn(Optional.empty());

        // When
        var result = traineeService.isUsernameAndPasswordMatching("unknownUser", "testPassword");

        // Then
        assertFalse(result);
        verify(traineeRepo, times(1)).findByUserName(stringArgumentCaptor.capture());
    }
}