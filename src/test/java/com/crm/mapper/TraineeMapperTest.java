package com.crm.mapper;

import com.crm.models.users.Trainee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TraineeMapperTest {
    private TraineeMapper traineeMapper;
    private Trainee testTrainee;

    @BeforeEach
    void setUp() {
        traineeMapper = Mappers.getMapper(TraineeMapper.class);
        testTrainee = Trainee.builder()
                .firstName("John")
                .lastName("Doe")
                .username("John.Doe")
                .password("12345")
                .isActive(true)
                .build();
    }

    @AfterEach
    void destroy() {
        traineeMapper = null;
        testTrainee = null;
    }

    @Test
    @DisplayName("Should be updated only non null fields when exists updated fields")
    void givenUpdatedTrainee_whenUpdateTrainee_thenOnlyNonNullFieldsShouldBeUpdated() {
        // Given
        var updatedTrainee = Trainee.builder()
                .firstName("Jane")
                .username("Jane.Doe")
                .build();

        // When
        traineeMapper.updateTrainee(testTrainee, updatedTrainee);

        // Then
        assertEquals("Jane", testTrainee.getFirstName());
        assertEquals("Doe", testTrainee.getLastName());
        assertEquals("Jane.Doe", testTrainee.getUsername());
        assertEquals("12345", testTrainee.getPassword());
    }

    @Test
    @DisplayName("Should not update any fields when all fields are null")
    void givenNullUpdatedTrainee_whenUpdateTrainee_thenNoFieldsShouldBeUpdated() {
        // Given
        var updatedTrainee = new Trainee();

        // When
        traineeMapper.updateTrainee(testTrainee, updatedTrainee);

        // Then
        assertEquals("John", testTrainee.getFirstName());
        assertEquals("Doe", testTrainee.getLastName());
        assertEquals("John.Doe", testTrainee.getUsername());
        assertEquals("12345", testTrainee.getPassword());
    }
}