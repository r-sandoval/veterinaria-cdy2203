package com.duoc.veterinaria.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTAuthenticationConfigTest {

    private final JWTAuthenticationConfig jwtAuthenticationConfig = new JWTAuthenticationConfig();

    @BeforeEach
    void setUp() {
        // Asignar la clave secreta manualmente para los tests
        Constants.SUPER_SECRET_KEY = "ZnJhc2VzbGFyZ2FzcGFyYWNvbG9jYXIjb21vY2xhdmlVbnVucHJvamVjdG9kZWwtZXBsb3BhcmFqd3Rjb25zcHJpbmdzZWN1cml0eQ==bWlwcnVlYmFkZWVqbXBsb3BhcmFjYXNIjQ=";
    }

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
