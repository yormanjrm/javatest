package com.test.java.application;

import com.test.java.domain.model.Pokemon;
import com.test.java.domain.ports.IPokemonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PokemonServiceTests {

    @Mock
    private IPokemonRepository iPokemonRepository;

    @Mock
    private Pokemon pokemonMock;

    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pokemonMock = new Pokemon();
    }

    @Test
    public void testFindByNameOrMainPokemon() {
        when(iPokemonRepository.findByName("Red")).thenReturn(pokemonMock);
        Pokemon pokemon = iPokemonRepository.findByName("Red");
        assertEquals(pokemonMock, pokemon);
        verify(iPokemonRepository, times(1)).findByName("Red");
    }

}