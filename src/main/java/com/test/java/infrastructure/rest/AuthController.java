package com.test.java.infrastructure.rest;

import com.test.java.application.AuthService;
import com.test.java.domain.model.User;
import com.test.java.infrastructure.dto.ApiResponseDTO;
import com.test.java.infrastructure.exception.InvalidPasswordException;
import com.test.java.infrastructure.exception.UserNotFoundException;
import com.test.java.infrastructure.jwt.JWTClient;
import com.test.java.infrastructure.jwt.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(AuthService authService, JWTGenerator jwtGenerator, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authService = authService;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<Object>> login(@RequestParam String email, @RequestParam String password) {
        try {
            User user = authService.findByEmail(email);
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtGenerator.getToken(user.getEmail(), user.getId());
                JWTClient jwtClient = new JWTClient(user.getId(), token);
                ApiResponseDTO<Object> response = new ApiResponseDTO<>(200, false, "User authenticated", jwtClient);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new InvalidPasswordException("Invalid password for email " + email);
            }
        } catch (UserNotFoundException e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(404, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (InvalidPasswordException e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(403, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

}