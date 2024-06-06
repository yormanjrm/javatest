package com.test.java.domain.ports;

import com.test.java.domain.model.Trainer;

public interface ITrainerRepository {
    Iterable<Trainer> findByNameOrMainPokemon(String filterText);
}