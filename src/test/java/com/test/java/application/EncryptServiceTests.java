package com.test.java.application;

import com.test.java.domain.ports.IEncryptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EncryptServiceTests {

    @Mock
    private IEncryptRepository iEncryptRepository;

    @InjectMocks
    private EncryptService encryptService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEncrypt() {
        when(iEncryptRepository.encrypt("ditto")).thenReturn("fKc8yukqwRaKomlpeWLHbcFpq/+lbS3RIx4BAhJB7hQ=");
        String encrypt = iEncryptRepository.encrypt("ditto");
        assertEquals("fKc8yukqwRaKomlpeWLHbcFpq/+lbS3RIx4BAhJB7hQ=", encrypt);
        verify(iEncryptRepository, times(1)).encrypt("ditto");
    }

}