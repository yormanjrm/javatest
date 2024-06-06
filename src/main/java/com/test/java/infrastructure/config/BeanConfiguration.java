package com.test.java.infrastructure.config;

import com.test.java.application.AuthService;
import com.test.java.domain.ports.IAuthRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public AuthService authService(IAuthRepository iAuthRepository){
        return new AuthService(iAuthRepository);
    }
}
