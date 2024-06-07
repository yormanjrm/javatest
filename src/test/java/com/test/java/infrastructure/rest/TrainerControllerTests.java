package com.test.java.infrastructure.rest;

import com.test.java.application.TrainerService;
import com.test.java.domain.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrainerControllerTests {

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    private MockMvc mockMvc;

    private List<Trainer> trainers;
    private Trainer trainer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
        trainer = new Trainer(1, "Red", "Charizard");
        trainers = Arrays.asList(trainer);
    }

    @Test
    void testFindByNameOrMainPokemon_SuccessWithoutFilter() throws Exception {
        when(trainerService.findByNameOrMainPokemon("")).thenReturn(trainers);
        mockMvc.perform(get("/api/trainers")
                        .param("filterText", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Trainers found"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testFindByNameOrMainPokemon_SuccessWithoutFilterAndNotRegister() throws Exception {
        when(trainerService.findByNameOrMainPokemon("")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/trainers")
                        .param("filterText", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("There are no registered trainers"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testFindByNameOrMainPokemon_SuccessWithFilterAndNotExists() throws Exception {
        when(trainerService.findByNameOrMainPokemon("Ash")).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/trainers")
                        .param("filterText", "Ash")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("No trainer matches the search value"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testFindByNameOrMainPokemon_SuccessWithFilter() throws Exception {
        when(trainerService.findByNameOrMainPokemon("Red")).thenReturn(trainers);
        mockMvc.perform(get("/api/trainers")
                        .param("filterText", "Red")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Trainers that match the search value"))
                .andExpect(jsonPath("$.data[0]").value(trainer));
    }

    @Test
    void testFindByNameOrMainPokemon_Exception() throws Exception {
        when(trainerService.findByNameOrMainPokemon(anyString())).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(get("/api/trainers")
                        .param("filterText", "errorFilter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("Unexpected error"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}