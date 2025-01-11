package com.crm;

import com.crm.models.training.Training;
import com.crm.models.users.Trainee;
import com.crm.models.users.Trainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

public abstract class UnitTestBase {
    // Captors
    @Captor
    protected ArgumentCaptor<Trainee> traineeArgumentCaptor;
    @Captor
    protected ArgumentCaptor<Trainer> trainerArgumentCaptor;
    @Captor
    protected ArgumentCaptor<Training> trainingArgumentCaptor;
    @Captor
    protected ArgumentCaptor<Long> idArgumentCaptor;

    private AutoCloseable mocks;

    @BeforeEach
    void initMocks() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeMocks() throws Exception {
        mocks.close();
    }
}
