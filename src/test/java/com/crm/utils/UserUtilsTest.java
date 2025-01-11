package com.crm.utils;

import com.crm.models.users.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilsTest {
    private Trainee testUser;

    @BeforeEach
    void setUp() {
        testUser = Trainee.builder()
                .firstName("John")
                .lastName("Doe")
                .userId(1L)
                .build();
    }

    @Test
    @DisplayName("generateUniqueUsername should create a unique username without conflicts")
    void generateUniqueUsername_ShouldCreateUniqueUsername_WhenNoConflicts() {
        // Given
        Set<String> existingUsernames = new HashSet<>();
        Function<String, Boolean> usernameExistsChecker = existingUsernames::contains;

        // When
        String result = UserUtils.generateUniqueUsername(testUser, usernameExistsChecker);

        // Then
        assertEquals("John.Doe", result);
    }

    @Test
    @DisplayName("generateUniqueUsername should append counter for conflicting usernames")
    void generateUniqueUsername_ShouldAppendCounter_WhenConflictsExist() {
        // Given
        Set<String> existingUsernames = Set.of("John.Doe");
        Function<String, Boolean> usernameExistsChecker = existingUsernames::contains;

        // When
        String result = UserUtils.generateUniqueUsername(testUser, usernameExistsChecker);

        // Then
        assertEquals("John.Doe1", result);
    }

    @Test
    @DisplayName("generateUniqueUsername should handle multiple conflicts correctly")
    void generateUniqueUsername_ShouldHandleMultipleConflicts() {
        // Given
        Set<String> existingUsernames = Set.of("John.Doe", "John.Doe1", "John.Doe2");
        Function<String, Boolean> usernameExistsChecker = existingUsernames::contains;

        // When
        String result = UserUtils.generateUniqueUsername(testUser, usernameExistsChecker);

        // Then
        assertEquals("John.Doe3", result);
    }

    @Test
    @DisplayName("generatePassword should generate password of correct length")
    void generatePassword_ShouldGeneratePasswordOfCorrectLength() {
        // When
        String password = UserUtils.generatePassword();

        // Then
        assertNotNull(password);
        assertEquals(10, password.length());
    }

    @Test
    @DisplayName("generatePassword should generate unique passwords on multiple calls")
    void generatePassword_ShouldGenerateUniquePasswords() {
        // When
        String password1 = UserUtils.generatePassword();
        String password2 = UserUtils.generatePassword();

        // Then
        assertNotNull(password1);
        assertNotNull(password2);
        assertNotEquals(password1, password2);
    }
}