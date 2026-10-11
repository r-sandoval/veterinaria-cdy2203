package com.duoc.veterinaria.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebSecurityConfigTest {

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
