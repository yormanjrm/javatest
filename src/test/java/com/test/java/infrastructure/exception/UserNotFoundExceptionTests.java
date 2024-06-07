package com.test.java.infrastructure.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserNotFoundExceptionTests {
    @Test
    void testUserNotFoundExceptionMessage() {
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }
}