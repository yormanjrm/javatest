package com.test.java.infrastructure.adapter;

import com.test.java.domain.model.User;
import com.test.java.infrastructure.entity.UserEntity;
import com.test.java.infrastructure.exception.UserNotFoundException;
import com.test.java.infrastructure.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthCrudRepositoryImpTests {

    @Mock
    private IAuthCrudRepository iAuthCrudRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthCrudRepositoryImp authCrudRepositoryImp;

    private User userMock;
    private UserEntity userEntityMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userMock = new User(1, "John Doe", "john.doe@example.com", "$2a$10$/iOt2Bx.vd4PHhWWJttE3.mNlX/jriVf4dASqOwbijXR.0goXmyRe");
        userEntityMock = new UserEntity(1, "John Doe", "john.doe@example.com", "$2a$10$/iOt2Bx.vd4PHhWWJttE3.mNlX/jriVf4dASqOwbijXR.0goXmyRe");
    }

    @Test
    public void testFindUserByEmail_Success() {
        when(iAuthCrudRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(userEntityMock));
        when(userMapper.toUser(userEntityMock)).thenReturn(userMock);
        User foundUser = authCrudRepositoryImp.findByEmail("john.doe@example.com");
        assertNotNull(foundUser);
        assertEquals(userMock, foundUser);
        verify(iAuthCrudRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    public void testFindUserByEmail_UserNotFoundException() {
        when(iAuthCrudRepository.findByEmail("test@mail.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> authCrudRepositoryImp.findByEmail("test@mail.com"));
        verify(iAuthCrudRepository, times(1)).findByEmail("test@mail.com");
    }

}
