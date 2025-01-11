package com.crm.services.impl;

import com.crm.UnitTestBase;
import com.crm.mapper.TrainerMapper;
import com.crm.models.users.Trainer;
import com.crm.repositories.TrainerRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest extends UnitTestBase {
    @Mock
    private TrainerRepo trainerRepo;
    @Mock
    private TrainerMapper trainerMapper;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer testTrainer;

    @BeforeEach
    void setUp() {
        testTrainer = Trainer.builder()
                .userId(1L)
                .firstName("Alice")
                .lastName("Smith")
                .username("Alice.Smith")
                .specialization("testSpecialization")
                .password("SomePassword")
                .build();
    }

    @AfterEach
    void destroy() {
        testTrainer = null;
    }

    @Test
    @DisplayName("findById should return trainer when exists")
    void findById_ShouldReturnTrainer_WhenExists() {
        // Given
        when(trainerRepo.findById(anyLong())).thenReturn(Optional.of(testTrainer));

        // When
        var result = trainerService.findById(testTrainer.getUserId());

        // Then
        assertEquals(testTrainer, result);
        verify(trainerRepo, times(1)).findById(idArgumentCaptor.capture());
    }

    @Test
    @DisplayName("save should generate username and password, then save trainer")
    void save_ShouldGenerateUsernameAndPasswordAndSaveTrainer() {
        // Given
        String expectedUserName = "Alice.Smith";

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
        String expectedUserName = "Alice.Smith";

        when(trainerRepo.save(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.save("Alice", "Smith", "testSpecialization");

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
        when(trainerRepo.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                NoSuchElementException.class,
                () -> trainerService.update(new Trainer()),
                "Trainer with id=1 not found, updating failed"
        );

        verify(trainerRepo, times(1)).findById(idArgumentCaptor.capture());
        verify(trainerRepo, never()).update(trainerArgumentCaptor.capture());
    }

    @Test
    @DisplayName("update should update trainer if exists")
    void update_ShouldUpdateTrainer_IfExists() {
        // Given
        when(trainerRepo.findById(anyLong())).thenReturn(Optional.of(testTrainer));
        doNothing().when(trainerMapper).updateTrainer(any(Trainer.class), any(Trainer.class));
        when(trainerRepo.update(any(Trainer.class))).thenReturn(testTrainer);

        // When
        var result = trainerService.update(new Trainer());

        // Then
        assertEquals(testTrainer, result);
        verify(trainerRepo, times(1)).findById(idArgumentCaptor.capture());
        verify(trainerMapper, times(1)).updateTrainer(
                trainerArgumentCaptor.capture(),
                trainerArgumentCaptor.capture()
        );
        verify(trainerRepo, times(1)).update(trainerArgumentCaptor.capture());
    }
}