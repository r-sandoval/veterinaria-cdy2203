package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.config.JWTAuthenticationConfig;
import com.duoc.veterinaria.model.User;
import com.duoc.veterinaria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("user") String username,
                                        @RequestParam("encryptedPass") String encryptedPass) {
        try {
            User user = (User) userService.loadUserByUsername(username);

            if (!passwordEncoder.matches(encryptedPass, user.getPassword())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciales inválidas");
            }

            String token = jwtAuthenticationConfig.getJWTToken(username);
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciales inválidas");
        }
    }
}