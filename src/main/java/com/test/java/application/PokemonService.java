package com.test.java.application;

import com.test.java.domain.ports.IPokemonRepository;
import com.test.java.domain.model.Pokemon;

public class PokemonService {
    private final IPokemonRepository iPokemonRepository;

    public PokemonService(IPokemonRepository iPokemonRepository) {
        this.iPokemonRepository = iPokemonRepository;
    }

    public Pokemon findByName(String name) {
        return iPokemonRepository.findByName(name);
    }
}