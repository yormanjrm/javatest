package com.test.java.infrastructure.adapter;

import com.test.java.domain.model.Trainer;
import com.test.java.infrastructure.mapper.TrainerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TrainerCrudRepositoryImpTests {
    @Mock
    private ITrainerCrudRepository iTrainerCrudRepository;

    @Mock
    private TrainerMapper trainerMapper;

    @InjectMocks
    private TrainerCrudRepositoryImp trainerCrudRepositoryImp;

    private List<Trainer> trainers;
    private Trainer trainer1;
    private Trainer trainer2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer1 = new Trainer(1, "Ash", "Pikachu");
        trainer2 = new Trainer(2, "Red", "Charizard");
        trainers = Arrays.asList(trainer1, trainer2);
    }

    @Test
    void testFindByNameOrMainPokemon_withFilter() {
        when(iTrainerCrudRepository.findByNameOrMainPokemon("Red")).thenReturn(Collections.emptyList());
        when(trainerMapper.toTrainers(anyList())).thenReturn(trainers);
        Iterable<Trainer> result = trainerCrudRepositoryImp.findByNameOrMainPokemon("Red");
        assertEquals(trainers, result, "The returned trainers should match the expected trainers");
        verify(iTrainerCrudRepository, times(1)).findByNameOrMainPokemon("Red");
        verify(trainerMapper, times(1)).toTrainers(anyList());
    }

    @Test
    void testFindByNameOrMainPokemon_noFilter() {
        when(iTrainerCrudRepository.findAll()).thenReturn(Collections.emptyList());
        when(trainerMapper.toTrainers(anyList())).thenReturn(trainers);
        Iterable<Trainer> result = trainerCrudRepositoryImp.findByNameOrMainPokemon("");
        assertEquals(trainers, result, "The returned trainers should match the expected trainers");
        verify(iTrainerCrudRepository, times(1)).findAll();
        verify(trainerMapper, times(1)).toTrainers(anyList());
    }
}