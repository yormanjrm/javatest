package com.test.java.infrastructure.rest;

import com.test.java.application.PokemonService;
import com.test.java.domain.model.Pokemon;
import com.test.java.infrastructure.dto.ApiResponseDTO;
import com.test.java.infrastructure.exception.PokemonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external-api/pokeapi")
public class PokemonController {
    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<Object>> findByName(@RequestParam String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Pokemon name must not be empty");
            }
            Pokemon pokemon = pokemonService.findByName(name.toLowerCase());
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(200, false, "Pokemon found", pokemon);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PokemonNotFoundException e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(404, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(400, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(500, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}