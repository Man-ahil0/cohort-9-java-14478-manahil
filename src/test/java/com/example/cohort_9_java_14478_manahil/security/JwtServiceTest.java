package com.example.cohort_9_java_14478_manahil.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        userDetails = User.withUsername("manahil@example.com")
                .password("password123")
                .roles("USER")
                .build();
    }

    @Test
    void generateToken_shouldGenerateValidToken() {

        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_shouldReturnCorrectUsername() {

        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertEquals("manahil@example.com", username);
    }

    @Test
    void extractExpiration_shouldReturnFutureDate() {

        String token = jwtService.generateToken(userDetails);

        Date expiration = jwtService.extractExpiration(token);

        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {

        String token = jwtService.generateToken(userDetails);

        boolean result =
                jwtService.isTokenValid(token, userDetails);

        assertTrue(result);
    }

    @Test
    void isTokenValid_shouldReturnFalseForDifferentUser() {

        String token = jwtService.generateToken(userDetails);

        UserDetails differentUser =
                User.withUsername("other@example.com")
                        .password("password123")
                        .roles("USER")
                        .build();

        boolean result =
                jwtService.isTokenValid(token, differentUser);

        assertFalse(result);
    }

    @Test
    void extractUsername_shouldReturnCorrectEmailFromToken() {

        UserDetails anotherUser =
                User.withUsername("test@example.com")
                        .password("password123")
                        .roles("USER")
                        .build();

        String token = jwtService.generateToken(anotherUser);

        assertEquals(
                "test@example.com",
                jwtService.extractUsername(token)
        );
    }
}