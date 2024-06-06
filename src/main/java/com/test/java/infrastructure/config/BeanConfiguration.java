package com.test.java.infrastructure.config;

import com.test.java.application.AuthService;
import com.test.java.application.TrainerService;
import com.test.java.domain.ports.IAuthRepository;
import com.test.java.domain.ports.ITrainerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public AuthService authService(IAuthRepository iAuthRepository){
        return new AuthService(iAuthRepository);
    }
    @Bean
    public TrainerService trainerService(ITrainerRepository iTrainerRepository){
        return  new TrainerService(iTrainerRepository);
    }
}