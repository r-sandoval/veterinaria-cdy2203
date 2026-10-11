package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.User;
import com.duoc.veterinaria.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testLoadUserByUsername() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("hashed");
        user.setRole("ADMIN");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("admin");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        verify(userRepository, times(1)).findByUsername("admin");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("unknown");
        });

        verify(userRepository, times(1)).findByUsername("unknown");
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("newuser");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }
}