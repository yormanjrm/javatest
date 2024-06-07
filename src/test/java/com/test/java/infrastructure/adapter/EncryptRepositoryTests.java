package com.test.java.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EncryptRepositoryTests {

    @InjectMocks
    private EncryptRepository encryptRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEncrypt_success() throws Exception {
        String input = "HelloWorld";
        String encryptedText = encryptRepository.encrypt(input);
        assertNotNull(encryptedText, "The encrypted text should not be null");
    }

    @Test
    void testEncrypt_failure() {
        assertThrows(RuntimeException.class, () -> {
            encryptRepository.encrypt(null);
        }, "Encrypting a null value should throw RuntimeException");
    }
}
