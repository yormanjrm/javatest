package com.test.java.infrastructure.adapter;

import com.test.java.domain.model.User;
import com.test.java.domain.ports.IAuthRepository;
import com.test.java.infrastructure.entity.UserEntity;
import com.test.java.infrastructure.exception.UserNotFoundException;
import com.test.java.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthCrudRepositoryImp implements IAuthRepository {

    private final IAuthCrudRepository iAuthCrudRepository;
    private final UserMapper userMapper;

    public AuthCrudRepositoryImp(IAuthCrudRepository iAuthCrudRepository, UserMapper userMapper) {
        this.iAuthCrudRepository = iAuthCrudRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> userEntityOptional = iAuthCrudRepository.findByEmail(email);
        if(userEntityOptional.isEmpty()){
            throw new UserNotFoundException("User with email " + email + " not found");
        } else {
            return userMapper.toUser(userEntityOptional.orElseThrow(() -> new RuntimeException("Error with the proccess")));
        }
    }
}