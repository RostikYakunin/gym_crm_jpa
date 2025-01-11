package com.crm.repositories.impl;

import com.crm.UnitTestBase;
import com.crm.models.training.Training;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingRepoImplTest extends UnitTestBase {
    @Mock
    private Map<Long, Training> mockDatabase;

    @InjectMocks
    private TrainingRepoImpl trainingRepo;

    private Training testTraining;

    @BeforeEach
    void setUp() {
        testTraining = Training.builder()
                .id(1L)
                .build();
    }

    @AfterEach
    void tearDown() {
        testTraining = null;
    }

    @Test
    @DisplayName("findById should return training when exists")
    void findById_ShouldReturnTraining_WhenExists() {
        // Given
        when(mockDatabase.get(anyLong())).thenReturn(testTraining);

        // When
        var result = trainingRepo.findById(testTraining.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals(testTraining, result.get());
        verify(mockDatabase, times(1)).get(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("findById should return empty optional when training does not exist")
    void findById_ShouldReturnEmptyOptional_WhenNotExists() {
        // Given
        when(mockDatabase.get(anyLong())).thenReturn(null);

        // When
        var result = trainingRepo.findById(testTraining.getId());

        // Then
        assertFalse(result.isPresent());
        verify(mockDatabase, times(1)).get(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("save should save the training")
    void save_ShouldSaveTraining() {
        // Given
        when(mockDatabase.put(anyLong(), any(Training.class))).thenReturn(testTraining);

        // When
        var result = trainingRepo.save(testTraining);

        // Then
        assertNotNull(result);
        verify(mockDatabase, times(1)).put(
                idArgumentCaptor.capture(),
                trainingArgumentCaptor.capture()
        );
    }

    @Test
    @DisplayName("update should throw exception")
    void update_ShouldThrowException() {
        // Given - When - Then
        assertThrows(
                NotImplementedException.class,
                () -> trainingRepo.update(testTraining),
                "Method update is not implemented yet... "
        );
    }

    @Test
    @DisplayName("delete should throw exception")
    void delete_ShouldThrowException() {
        // Given - When - Then
        assertThrows(
                NotImplementedException.class,
                () -> trainingRepo.delete(testTraining),
                "Method delete is not implemented yet... "
        );
    }

    @Test
    @DisplayName("isExistsById should return true when training exists")
    void isExistsById_ShouldReturnTrue_WhenTrainingExists() {
        // Given
        when(mockDatabase.containsKey(anyLong())).thenReturn(true);

        // When
        var result = trainingRepo.isExistsById(testTraining.getId());

        // Then
        assertTrue(result);
        verify(mockDatabase, times(1)).containsKey(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("isExistsById should return false when training does not exist")
    void isExistsById_ShouldReturnFalse_WhenTrainingNotExists() {
        // Given
        when(mockDatabase.containsKey(anyLong())).thenReturn(false);

        // When
        var result = trainingRepo.isExistsById(testTraining.getId());

        // Then
        assertFalse(result);
        verify(mockDatabase, times(1)).containsKey(idArgumentCaptor.capture());
    }
}