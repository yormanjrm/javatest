package com.test.java.infrastructure.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String message) {
        super(message);
    }
}