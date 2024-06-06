package com.test.java.infrastructure.rest;

import com.test.java.application.AuthService;
import com.test.java.domain.model.User;
import com.test.java.infrastructure.dto.ApiResponseDTO;
import com.test.java.infrastructure.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<Object>> login(@RequestParam String email, @RequestParam String password) {
        try {
            User user = authService.findByEmail(email);
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(200, false, "User authenticated", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(404, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(500, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}