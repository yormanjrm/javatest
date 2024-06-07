package com.test.java.infrastructure.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PokemonNotFoundExceptionTests {
    @Test
    void testInvalidPasswordExceptionMessage() {
        String message = "Invalid password";
        PokemonNotFoundException exception = new PokemonNotFoundException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }
}