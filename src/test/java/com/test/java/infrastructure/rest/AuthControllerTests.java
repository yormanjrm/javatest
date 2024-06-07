package com.test.java.infrastructure.rest;

import com.test.java.application.AuthService;
import com.test.java.domain.model.User;
import com.test.java.infrastructure.exception.UserNotFoundException;
import com.test.java.infrastructure.jwt.JWTGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTests {

    @Mock
    private AuthService authService;

    @Mock
    private JWTGenerator jwtGenerator;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthController authController;

    private User user;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        user = new User(1, "John Doe", "john.doe@example.com", "$2a$10$/iOt2Bx.vd4PHhWWJttE3.mNlX/jriVf4dASqOwbijXR.0goXmyRe");
    }

    @Test
    void testLogin_Success() throws Exception {
        when(authService.findByEmail("john.doe@example.com")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("password123", "$2a$10$/iOt2Bx.vd4PHhWWJttE3.mNlX/jriVf4dASqOwbijXR.0goXmyRe")).thenReturn(true);
        when(jwtGenerator.getToken("john.doe@example.com", 1)).thenReturn("anytoken");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        mockMvc.perform(post("/auth")
                        .param("email", "john.doe@example.com")
                        .param("password", "password123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("User authenticated"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.token").value("anytoken"));
    }

    @Test
    void testLogin_UserNotFound() throws Exception {
        when(authService.findByEmail("john.doe@example2.com")).thenThrow(new UserNotFoundException("User not found"));
        mockMvc.perform(post("/auth")
                        .param("email", "john.doe@example2.com")
                        .param("password", "password123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testLogin_InvalidPassword() throws Exception {
        when(authService.findByEmail("john.doe@example.com")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("wrongPassword", "$2a$10$/iOt2Bx.vd4PHhWWJttE3.mNlX/jriVf4dASqOwbijXR.0goXmyRe")).thenReturn(false);
        mockMvc.perform(post("/auth")
                        .param("email", "john.doe@example.com")
                        .param("password", "wrongPassword")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.message").value("Invalid password for email john.doe@example.com"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}