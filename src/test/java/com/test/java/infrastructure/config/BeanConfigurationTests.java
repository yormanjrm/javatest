package com.test.java.infrastructure.config;

import com.test.java.application.AuthService;
import com.test.java.application.EncryptService;
import com.test.java.application.PokemonService;
import com.test.java.application.TrainerService;
import com.test.java.domain.ports.IAuthRepository;
import com.test.java.domain.ports.IEncryptRepository;
import com.test.java.domain.ports.IPokemonRepository;
import com.test.java.domain.ports.ITrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BeanConfigurationTests {

    @MockBean
    private IAuthRepository iAuthRepository;

    @MockBean
    private ITrainerRepository iTrainerRepository;

    @MockBean
    private IPokemonRepository iPokemonRepository;

    @MockBean
    private IEncryptRepository iEncryptRepository;

    @Test
    void authServiceBeanShouldNotBeNull() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        AuthService authService = beanConfiguration.authService(iAuthRepository);
        assertNotNull(authService);
    }

    @Test
    void trainerServiceBeanShouldNotBeNull() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        TrainerService trainerService = beanConfiguration.trainerService(iTrainerRepository);
        assertNotNull(trainerService);
    }

    @Test
    void pokemonServiceBeanShouldNotBeNull() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        PokemonService pokemonService = beanConfiguration.pokemonService(iPokemonRepository);
        assertNotNull(pokemonService);
    }

    @Test
    void restTemplateBeanShouldNotBeNull() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        RestTemplate restTemplate = beanConfiguration.restTemplate();
        assertNotNull(restTemplate);
    }

    @Test
    void encryptServiceBeanShouldNotBeNull() {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        EncryptService encryptService = beanConfiguration.encryptService(iEncryptRepository);
        assertNotNull(encryptService);
    }

}