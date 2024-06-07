package com.test.java.infrastructure.rest;

import com.test.java.application.EncryptService;
import com.test.java.infrastructure.dto.ApiResponseDTO;
import com.test.java.infrastructure.dto.EncryptedDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encrypt")
public class EncryptController {
    private final EncryptService encryptService;

    public EncryptController(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<Object>> encrypt(@RequestParam String input) {
        try {
            if (input == null || input.trim().isEmpty()) {
                throw new IllegalArgumentException("Input must not be empty");
            }
            String encrypted = encryptService.encrypt(input);
            EncryptedDTO<String> encryptedDTO = new EncryptedDTO<>(input, encrypted);
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(200, false, "Entry successfully encrypted", encryptedDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(400, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(500, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}