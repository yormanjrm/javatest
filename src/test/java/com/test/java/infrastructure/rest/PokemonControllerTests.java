package com.test.java.infrastructure.rest;

import com.test.java.application.EncryptService;
import com.test.java.application.PokemonService;
import com.test.java.domain.model.Pokemon;
import com.test.java.infrastructure.exception.PokemonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PokemonControllerTests {

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private PokemonController pokemonController;

    private MockMvc mockMvc;

    private Pokemon pokemonMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pokemonController).build();
        pokemonMock = new Pokemon();
        pokemonMock.setName("Ditto");
    }

    @Test
    void testFindByName_Success() throws Exception {
        when(pokemonService.findByName("ditto")).thenReturn(pokemonMock);
        mockMvc.perform(get("/external-api/pokeapi")
                        .param("name", "ditto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Pokemon found"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void testFindByName_NotFound() throws Exception {
        when(pokemonService.findByName("agumon")).thenThrow(new PokemonNotFoundException("Pokemon not found: agumon"));
        mockMvc.perform(get("/external-api/pokeapi")
                        .param("name", "agumon")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Pokemon not found: agumon"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindByName_withoutName() throws Exception {
        when(pokemonService.findByName("")).thenThrow(new IllegalArgumentException("Pokemon name must not be empty"));
        mockMvc.perform(get("/external-api/pokeapi")
                        .param("name", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Pokemon name must not be empty"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindByName_Exception() throws Exception {
        when(pokemonService.findByName(anyString())).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(get("/external-api/pokeapi")
                        .param("name", "errorName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("Unexpected error"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}