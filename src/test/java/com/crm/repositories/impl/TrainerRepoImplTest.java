package com.crm.repositories.impl;

import com.crm.UnitTestBase;
import com.crm.models.users.Trainer;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerRepoImplTest extends UnitTestBase {
    @Mock
    private Map<Long, Trainer> mockDatabase;

    @InjectMocks
    private TrainerRepoImpl trainerRepo;

    private Trainer testTrainer;

    @BeforeEach
    void setUp() {
        testTrainer = Trainer.builder()
                .id(1L)
                .username("testTrainer")
                .build();
    }

    @AfterEach
    void tearDown() {
        testTrainer = null;
    }

    @Test
    @DisplayName("findById should return trainer when exists")
    void findById_ShouldReturnTrainer_WhenExists() {
        // Given
        when(mockDatabase.get(anyLong())).thenReturn(testTrainer);

        // When
        var result = trainerRepo.findById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testTrainer, result.get());
        verify(mockDatabase, times(1)).get(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("findById should return empty optional when trainer does not exist")
    void findById_ShouldReturnEmptyOptional_WhenNotExists() {
        // Given
        when(mockDatabase.get(anyLong())).thenReturn(null);

        // When
        var result = trainerRepo.findById(testTrainer.getId());

        // Then
        assertFalse(result.isPresent());
        verify(mockDatabase, times(1)).get(testTrainer.getId());
    }

    @Test
    @DisplayName("save should save the trainer")
    void save_ShouldSaveTrainer() {
        // Given
        when(mockDatabase.put(anyLong(), any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerRepo.save(testTrainer);

        // Then
        assertNotNull(result);
        verify(mockDatabase, times(1)).put(
                idArgumentCaptor.capture(),
                trainerArgumentCaptor.capture()
        );
    }

    @Test
    @DisplayName("update should update the trainer")
    void update_ShouldUpdateTrainer() {
        // Given
        when(mockDatabase.put(anyLong(), any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerRepo.update(testTrainer);

        // Then
        assertEquals(testTrainer, result);
        verify(mockDatabase, times(1)).put(
                idArgumentCaptor.capture(),
                trainerArgumentCaptor.capture()
        );
    }

    @Test
    @DisplayName("deleteById should throw exception")
    void deleteById_ShouldThrowException() {
        // Given - When - Then
        assertThrows(
                NotImplementedException.class,
                () -> trainerRepo.delete(testTrainer),
                "Method delete is not implemented yet... "
        );
    }

    @Test
    @DisplayName("isExistsById should return true when trainer exists")
    void isExistsById_ShouldReturnTrue_WhenTrainerExists() {
        // Given
        when(mockDatabase.containsKey(anyLong())).thenReturn(true);

        // When
        var result = trainerRepo.isExistsById(testTrainer.getId());

        // Then
        assertTrue(result);
        verify(mockDatabase, times(1)).containsKey(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("isExistsById should return false when trainer does not exist")
    void isExistsById_ShouldReturnFalse_WhenTrainerNotExists() {
        // Given
        when(mockDatabase.containsKey(anyLong())).thenReturn(false);

        // When
        var result = trainerRepo.isExistsById(testTrainer.getId());

        // Then
        assertFalse(result);
        verify(mockDatabase, times(1)).containsKey(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("isUserNameExists should return true when username exists")
    void isUserNameExists_ShouldReturnTrue_WhenUsernameExists() {
        // Given
        when(mockDatabase.values()).thenReturn(List.of(testTrainer));

        // When
        var result = trainerRepo.isUserNameExists(testTrainer.getUsername());

        // Then
        assertTrue(result);
        verify(mockDatabase, times(1)).values();
    }

    @Test
    @DisplayName("isUserNameExists should return false when username does not exist")
    void isUserNameExists_ShouldReturnFalse_WhenUsernameNotExists() {
        // Given
        when(mockDatabase.values()).thenReturn(Collections.emptyList());

        // When
        var result = trainerRepo.isUserNameExists(testTrainer.getUsername());

        // Then
        assertFalse(result);
        verify(mockDatabase, times(1)).values();
    }

}