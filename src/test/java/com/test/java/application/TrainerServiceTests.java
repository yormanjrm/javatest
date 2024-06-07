package com.test.java.application;

import com.test.java.domain.model.Trainer;
import com.test.java.domain.ports.ITrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainerServiceTests {

    @Mock
    private ITrainerRepository iTrainerRepository;

    @Mock
    private Trainer trainer;

    @Mock
    private List<Trainer> trainers;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        trainer = new Trainer(1, "Red", "Charizard");
        trainers = Arrays.asList(trainer);
    }

    @Test
    public void testFindByNameOrMainPokemon() {
        when(iTrainerRepository.findByNameOrMainPokemon("Red")).thenReturn(trainers);
        Iterable<Trainer> trainers_response = iTrainerRepository.findByNameOrMainPokemon("Red");
        assertEquals(trainers, trainers_response);
        verify(iTrainerRepository, times(1)).findByNameOrMainPokemon("Red");
    }

}