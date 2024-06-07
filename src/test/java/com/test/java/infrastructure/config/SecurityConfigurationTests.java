package com.test.java.infrastructure.config;

import com.test.java.infrastructure.dto.ApiResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"security.enabled=true"}, locations = "classpath:application.properties")
public class SecurityConfigurationTests {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    private String url;

    @BeforeEach
    void setUp() {
        url = "http://localhost:" + port;
    }

    private String loginAndGetToken() {
        return login("john.doe@example.com", "password123");
    }

    private String login(String email, String password) {
        String loginURL = url + "/auth";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", email);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<ApiResponseDTO> response = testRestTemplate.postForEntity(loginURL, request, ApiResponseDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ApiResponseDTO<?> body = response.getBody();
            if (body.getData() != null) {
                Map<String, Object> data = (Map<String, Object>) body.getData();
                String token = (String) data.get("token");
                return token.replace("Bearer ", "");
            }
        }
        return null;
    }

    @Test
    void testLoginEndpoint() {
        String loginURL = url + "/auth";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", "john.doe@example.com");
        params.add("password", "password123");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> res = testRestTemplate.withBasicAuth("abc", "def").postForEntity(loginURL, request, String.class);
        Assertions.assertEquals(HttpStatus.OK.value(), res.getStatusCode().value());
    }

    @Test
    void testLoginEndpoint_InvalidUser() {
        String loginURL = url + "/auth";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", "john.doe3@example.com");
        params.add("password", "password123");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> res = testRestTemplate.withBasicAuth("abc", "def").postForEntity(loginURL, request, String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), res.getStatusCode().value());
    }

    @Test
    void testLoginEndpoint_InvalidPassword() {
        String loginURL = url + "/auth";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", "john.doe@example.com");
        params.add("password", "password1234");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> res = testRestTemplate.withBasicAuth("abc", "def").postForEntity(loginURL, request, String.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), res.getStatusCode().value());
    }

    @Test
    void testGetTrainers_Unauthenticated() {
        String getATrainer = url + "/api/trainers?filterText=red";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = testRestTemplate.exchange(getATrainer, HttpMethod.GET, request, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatusCode().value());
    }

    @Test
    void testGetTrainers_Authenticated() {
        String token = loginAndGetToken();
        Assertions.assertNotNull(token, "Token should not be null");
        String getATrainer = url + "/api/trainers?filterText=Red";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = testRestTemplate.exchange(getATrainer, HttpMethod.GET, request, String.class);
        Assertions.assertEquals(HttpStatus.OK.value(), res.getStatusCode().value());
    }

    @Test
    void testGetAPokemonFromPokeApi_Unauthenticated() {
        String getAPokemon = url + "/external-api/pokeapi?name=Ditto";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = testRestTemplate.exchange(getAPokemon, HttpMethod.GET, request, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatusCode().value());
    }

    @Test
    void testGetAPokemonFromPokeApi_Authenticated() {
        String token = loginAndGetToken();
        Assertions.assertNotNull(token, "Token should not be null");
        String getAPokemon = url + "/external-api/pokeapi?name=Ditto";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = testRestTemplate.exchange(getAPokemon, HttpMethod.GET, request, String.class);
        Assertions.assertEquals(HttpStatus.OK.value(), res.getStatusCode().value());
    }

    @Test
    void testEncrypt_Unauthenticated() {
        String postEncryptURL = url + "/encrypt?input=Ditto";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = testRestTemplate.exchange(postEncryptURL, HttpMethod.POST, request, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatusCode().value());
    }

    @Test
    void testEncrypt_Authenticated() {
        String token = loginAndGetToken();
        Assertions.assertNotNull(token, "Token should not be null");
        String postEncryptURL = url + "/encrypt?input=Ditto";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> res = testRestTemplate.exchange(postEncryptURL, HttpMethod.POST, request, String.class);
        Assertions.assertEquals(HttpStatus.OK.value(), res.getStatusCode().value());
    }

}
