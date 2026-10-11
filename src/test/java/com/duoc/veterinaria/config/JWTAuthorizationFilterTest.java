package com.duoc.veterinaria.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthorizationFilterTest {

    @InjectMocks
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    void testDoFilterInternalWithoutToken() throws Exception {
        when(request.getHeader(Constants.HEADER_AUTHORIZATION_KEY)).thenReturn(null);

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternalWithInvalidToken() throws Exception {
        when(request.getHeader(Constants.HEADER_AUTHORIZATION_KEY)).thenReturn("InvalidToken");

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        SecurityContextHolder.clearContext();
    }
}
