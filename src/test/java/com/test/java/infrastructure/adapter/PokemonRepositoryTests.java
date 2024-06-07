package com.test.java.infrastructure.adapter;

import com.test.java.domain.model.Pokemon;
import com.test.java.infrastructure.exception.PokemonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PokemonRepositoryTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonRepository pokemonRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByName_success() {
        Pokemon mockPokemon = new Pokemon();
        mockPokemon.setName("pikachu");
        when(restTemplate.getForObject(anyString(), eq(Pokemon.class))).thenReturn(mockPokemon);
        Pokemon result = pokemonRepository.findByName("pikachu");
        assertNotNull(result, "The returned Pokemon should not be null");
        assertEquals("pikachu", result.getName(), "The name of the returned Pokemon should be 'pikachu'");
    }

    @Test
    void testFindByName_notFound() {
        when(restTemplate.getForObject(anyString(), eq(Pokemon.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        Exception exception = assertThrows(PokemonNotFoundException.class, () -> {
            pokemonRepository.findByName("unknown");
        });
        String expectedMessage = "Pokemon not found: unknown";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "The exception message should contain the expected message");
    }

    @Test
    void testFindByName_otherError() {
        when(restTemplate.getForObject(anyString(), eq(Pokemon.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThrows(HttpClientErrorException.class, () -> {
            pokemonRepository.findByName("error");
        });
    }
}
