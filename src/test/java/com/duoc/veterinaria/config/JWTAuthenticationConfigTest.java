package com.duoc.veterinaria.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTAuthenticationConfigTest {

    private final JWTAuthenticationConfig jwtAuthenticationConfig = new JWTAuthenticationConfig();

    @Test
    void testGetJWTToken() {
        String token = jwtAuthenticationConfig.getJWTToken("admin");

        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));
    }

    @Test
    void testConstants() {
        assertNotNull(Constants.LOGIN_URL);
        assertNotNull(Constants.HEADER_AUTHORIZATION_KEY);
        assertNotNull(Constants.TOKEN_BEARER_PREFIX);
        assertNotNull(Constants.SUPER_SECRET_KEY);
        assertTrue(Constants.TOKEN_EXPIRATION_TIME > 0);
    }

    @Test
    void testGetSigningKey() {
        assertNotNull(Constants.getSigningKey(Constants.SUPER_SECRET_KEY));
    }
}
