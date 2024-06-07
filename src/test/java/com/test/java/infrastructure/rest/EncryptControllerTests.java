package com.test.java.infrastructure.rest;

import com.test.java.application.EncryptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EncryptControllerTests {

    @Mock
    private EncryptService encryptService;

    @InjectMocks
    private EncryptController encryptController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(encryptController).build();
    }

    @Test
    void testEncrypt_Success() throws Exception {
        when(encryptService.encrypt("ditto")).thenReturn(anyString());
        mockMvc.perform(post("/encrypt")
                        .param("input", "ditto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Entry successfully encrypted"))
                .andExpect(jsonPath("$.data.input").value("ditto"))
                .andExpect(jsonPath("$.data.encrypted").value(anyString()));
    }


    @Test
    void testEncrypt_withoutInput() throws Exception {
        when(encryptService.encrypt("")).thenThrow(new IllegalArgumentException("Input must not be empty"));
        mockMvc.perform(post("/encrypt")
                        .param("input", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Input must not be empty"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testEncrypt_Exception() throws Exception {
        when(encryptService.encrypt(anyString())).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(post("/encrypt")
                        .param("input", "errorInput")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("Unexpected error"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}