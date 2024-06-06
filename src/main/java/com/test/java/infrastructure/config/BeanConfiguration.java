package com.test.java.infrastructure.config;

import com.test.java.application.AuthService;
import com.test.java.application.EncryptService;
import com.test.java.application.PokemonService;
import com.test.java.application.TrainerService;
import com.test.java.domain.ports.IAuthRepository;
import com.test.java.domain.ports.IEncryptRepository;
import com.test.java.domain.ports.IPokemonRepository;
import com.test.java.domain.ports.ITrainerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    public AuthService authService(IAuthRepository iAuthRepository) {
        return new AuthService(iAuthRepository);
    }

    @Bean
    public TrainerService trainerService(ITrainerRepository iTrainerRepository) {
        return new TrainerService(iTrainerRepository);
    }

    @Bean
    public PokemonService pokemonService(IPokemonRepository iPokemonRepository) {
        return new PokemonService(iPokemonRepository);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public EncryptService encryptService(IEncryptRepository iEncryptRepository) {
        return new EncryptService(iEncryptRepository);
    }
}