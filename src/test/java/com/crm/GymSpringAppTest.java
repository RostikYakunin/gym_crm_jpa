package com.crm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
class GymSpringAppTest {
    @Test
    @DisplayName("Should successfully load application context")
    void contextLoads() {
        // Given - When - Then
        assertDoesNotThrow(() -> GymSpringApp.main(new String[]{}));
    }
}