package com.test.java.infrastructure.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvalidPasswordExceptionTests {
    @Test
    void testInvalidPasswordExceptionMessage() {
        String message = "Invalid password";
        InvalidPasswordException exception = new InvalidPasswordException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }
}