package com.test.java.application;

import com.test.java.domain.ports.IEncryptRepository;

public class EncryptService {
    private final IEncryptRepository iEncryptRepository;

    public EncryptService(IEncryptRepository iEncryptRepository) {
        this.iEncryptRepository = iEncryptRepository;
    }

    public String encrypt(String input) {
        return iEncryptRepository.encrypt(input);
    }
}