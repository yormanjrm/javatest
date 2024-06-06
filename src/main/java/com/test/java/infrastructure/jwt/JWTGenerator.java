package com.test.java.infrastructure.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;
import static com.test.java.infrastructure.jwt.Constants.*;

@Service
public class JWTGenerator {
    public String getToken(String username, Integer userId) {
        String token = Jwts.builder()
                .setId("testjava")
                .setSubject(username)
                .claim("id", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getSignedKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512).compact();

        return "Bearer " + token;
    }
}