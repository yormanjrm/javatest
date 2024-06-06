package com.test.java.application;

import com.test.java.domain.model.Trainer;
import com.test.java.domain.ports.ITrainerRepository;

public class TrainerService {
    private final ITrainerRepository iTrainerRepository;

    public TrainerService(ITrainerRepository iTrainerRepository) {
        this.iTrainerRepository = iTrainerRepository;
    }

    public Iterable<Trainer> findByNameOrMainPokemon(String filterText){
        return iTrainerRepository.findByNameOrMainPokemon(filterText);
    }
}