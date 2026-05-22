package com.openclassrooms.etudiant.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // 32 caracters for HS256
        ReflectionTestUtils.setField(jwtService, "secret", "12345678901234567890123456789012");
        ReflectionTestUtils.setField(jwtService, "expirationMs", 3600000L);
    }

    @Test
    void testGenerateToken() {
        UserDetails user = Mockito.mock(UserDetails.class);
        Mockito.when(user.getUsername()).thenReturn("etudiant");

        String token = jwtService.generateToken(user);

        assertThat(token).isNotNull();
        assertThat(jwtService.extractUsername(token)).isEqualTo("etudiant");
    }

    @Test
    void testIsTokenValid() {
        UserDetails user = Mockito.mock(UserDetails.class);
        Mockito.when(user.getUsername()).thenReturn("etudiant");

        String token = jwtService.generateToken(user);

        assertThat(jwtService.isTokenValid(token, user)).isTrue();

        UserDetails otherUser = Mockito.mock(UserDetails.class);
        Mockito.when(otherUser.getUsername()).thenReturn("autre");
        assertThat(jwtService.isTokenValid(token, otherUser)).isFalse();
    }

    @Test
    void testInvalidTokenHandling() {
        UserDetails user = Mockito.mock(UserDetails.class);
        assertThat(jwtService.isTokenValid("invalid.token.here", user)).isFalse();
    }
}