package com.duoc.veterinaria.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class Constants {

    // Spring Security
    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final String ISSUER_INFO = "https://www.duocuc.cl/";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 días

    // Clave secreta inyectada desde application.properties
    public static String SUPER_SECRET_KEY;

    @Value("${app.jwt.secret}")
    public void setSuperSecretKey(String secret) {
        Constants.SUPER_SECRET_KEY = secret;
    }

    public static Key getSigningKeyB64(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Key getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
