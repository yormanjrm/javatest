package com.test.java.application;

import com.test.java.domain.model.User;
import com.test.java.domain.ports.IAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceTests {

    @Mock
    private IAuthRepository iAuthRepository;

    @Mock
    private User user;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        user = new User(1, "John Doe", "john.doe@example.com", "$2a$10$/iOt2Bx.vd4PHhWWJttE3.mNlX/jriVf4dASqOwbijXR.0goXmyRe");
    }

    @Test
    public void testFindByEmail() {
        when(iAuthRepository.findByEmail("john.doe@example.com")).thenReturn(user);
        User foundUser = iAuthRepository.findByEmail("john.doe@example.com");
        assertEquals(user, foundUser);
        verify(iAuthRepository, times(1)).findByEmail("john.doe@example.com");
    }

}