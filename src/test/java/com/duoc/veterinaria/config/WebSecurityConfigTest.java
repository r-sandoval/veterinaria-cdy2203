package com.duoc.veterinaria.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebSecurityConfigTest {

    @BeforeEach
    void setUp() {
        Constants.SUPER_SECRET_KEY = "ZnJhc2VzbGFyZ2FzcGFyYWNvbG9jYXIjb21vY2xhdmlVbnVucHJvamVjdG9kZWwtZXBsb3BhcmFqd3Rjb25zcHJpbmdzZWN1cml0eQ==bWlwcnVlYmFkZWVqbXBsb3BhcmFjYXNIjQ=";
    }

    @Test
    void testPasswordEncoder() {
        WebSecurityConfig config = new WebSecurityConfig();
        assertNotNull(config.passwordEncoder());
    }

    @Test
    void testConstantsClass() {
        assertNotNull(Constants.getSigningKeyB64(Constants.SUPER_SECRET_KEY));
    }
}
