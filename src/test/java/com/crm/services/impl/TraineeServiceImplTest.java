package com.crm.services.impl;

import com.crm.UnitTestBase;
import com.crm.models.users.Trainee;
import com.crm.repositories.TraineeRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee testTrainee;

    @BeforeEach
    void setUp() {
        testTrainee = Trainee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("John.Doe")
                .address("testAddress")
                .password("somePassword")
                .dateOfBirth(LocalDate.of(1998, 11, 11))
                .build();
    }

    @AfterEach
    void destroy() {
        testTrainee = null;
    }

    @Test
    @DisplayName("findById should return trainee when exists")
    void findById_ShouldReturnTrainee_WhenExists() {
        // Given
        when(traineeRepo.findById(anyLong())).thenReturn(Optional.of(testTrainee));

        // When
        var result = traineeService.findById(testTrainee.getId());

        // Then
        assertEquals(testTrainee, result);
        verify(traineeRepo, times(1)).findById(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("save - should save trainee using parameters")
    void save_ShouldSaveTraineeUsingParameters() {
        // Given
        String expectedUserName = "John.Doe";

        when(traineeRepo.save(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.save("John", "Doe", "testAddress", LocalDate.of(1998, 11, 11));

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
        String expectedUserName = "John.Doe";

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
        traineeService.delete(new Trainee());

        // Then
        verify(traineeRepo, times(1)).delete(traineeArgumentCaptor.capture());
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
        when(traineeRepo.findById(anyLong())).thenReturn(Optional.of(testTrainee));
        when(traineeRepo.update(any(Trainee.class))).thenReturn(testTrainee);

        // When
        var result = traineeService.update(new Trainee());

        // Then
        assertNotNull(result);
        assertEquals(testTrainee, result);

        verify(traineeRepo, times(1)).update(traineeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("update should throw Exception when trainee was not found")
    void update_ShouldThrowException_WhenTraineeWasNotFound() {
        // Given
        when(traineeRepo.findById(anyLong())).thenReturn(Optional.empty());

        // When - Then
        assertThrows(
                NoSuchElementException.class,
                () -> traineeService.update(new Trainee()),
                "Trainee with id=1 not found"
        );

        verify(traineeRepo, never()).update(traineeArgumentCaptor.capture());
    }
}