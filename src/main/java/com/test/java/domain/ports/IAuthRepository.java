package com.test.java.domain.ports;

import com.test.java.domain.model.User;

public interface IAuthRepository {
    User findByEmail(String email);
}