package com.test.java.domain.ports;

import com.test.java.domain.model.Pokemon;

public interface IPokemonRepository {
    Pokemon findByName(String name);
}