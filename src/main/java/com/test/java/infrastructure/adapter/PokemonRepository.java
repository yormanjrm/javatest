package com.test.java.infrastructure.adapter;

import com.test.java.domain.model.Pokemon;
import com.test.java.domain.ports.IPokemonRepository;
import com.test.java.infrastructure.exception.PokemonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Repository
public class PokemonRepository implements IPokemonRepository {
    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon/";
    private final RestTemplate restTemplate;

    public PokemonRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Pokemon findByName(String name) {
        String url = POKEAPI_URL + name;
        try {
            return restTemplate.getForObject(url, Pokemon.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PokemonNotFoundException("Pokemon not found: " + name);
            } else {
                throw e;
            }
        }
    }
}