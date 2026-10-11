package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.config.JWTAuthenticationConfig;
import com.duoc.veterinaria.model.User;
import com.duoc.veterinaria.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testLoginSuccess() throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("hashedPassword");
        user.setRole("ADMIN");

        when(userService.loadUserByUsername("admin")).thenReturn(user);
        when(passwordEncoder.matches("admin123", "hashedPassword")).thenReturn(true);
        when(jwtAuthenticationConfig.getJWTToken("admin")).thenReturn("Bearer test-token");

        mockMvc.perform(post("/login")
                        .param("user", "admin")
                        .param("encryptedPass", "admin123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Bearer test-token"));
    }

    @Test
    void testLoginInvalidPassword() throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("hashedPassword");
        user.setRole("ADMIN");

        when(userService.loadUserByUsername("admin")).thenReturn(user);
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        mockMvc.perform(post("/login")
                        .param("user", "admin")
                        .param("encryptedPass", "wrongPassword"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testLoginUserNotFound() throws Exception {
        when(userService.loadUserByUsername("unknown")).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(post("/login")
                        .param("user", "unknown")
                        .param("encryptedPass", "anyPassword"))
                .andExpect(status().isForbidden());
    }
}