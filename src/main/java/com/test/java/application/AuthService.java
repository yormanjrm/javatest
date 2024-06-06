package com.test.java.application;

import com.test.java.domain.model.User;
import com.test.java.domain.ports.IAuthRepository;

public class AuthService {
    private final IAuthRepository iAuthRepository;

    public AuthService(IAuthRepository iAuthRepository) {
        this.iAuthRepository = iAuthRepository;
    }

    public User findByEmail(String email){
        return iAuthRepository.findByEmail(email);
    }
}