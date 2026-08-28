package com.example.cohort_9_java_14478_manahil.security;

import com.example.cohort_9_java_14478_manahil.entity.User;
import com.example.cohort_9_java_14478_manahil.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User();

        user.setId(1L);
        user.setFirstName("Manahil");
        user.setLastName("Waheed");
        user.setEmail("manahil@example.com");
        user.setPhoneNumber("03001234567");
        user.setPassword("encodedPassword");
        user.setRole("USER");
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {

        when(userRepository.findByEmail("manahil@example.com"))
                .thenReturn(Optional.of(user));

        UserDetails result =
                userDetailsService.loadUserByUsername(
                        "manahil@example.com"
                );

        assertNotNull(result);
        assertEquals(
                "manahil@example.com",
                result.getUsername()
        );
        assertEquals(
                "encodedPassword",
                result.getPassword()
        );
        assertTrue(
                result.getAuthorities()
                        .stream()
                        .anyMatch(a ->
                                a.getAuthority().equals("ROLE_USER"))
        );

        verify(userRepository)
                .findByEmail("manahil@example.com");
    }

    @Test
    void loadUserByUsername_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(
                        "unknown@example.com"
                )
        );

        verify(userRepository)
                .findByEmail("unknown@example.com");
    }

    @Test
    void loadUserByUsername_shouldUseUserRole() {

        user.setRole("ADMIN");

        when(userRepository.findByEmail("manahil@example.com"))
                .thenReturn(Optional.of(user));

        UserDetails result =
                userDetailsService.loadUserByUsername(
                        "manahil@example.com"
                );

        assertTrue(
                result.getAuthorities()
                        .stream()
                        .anyMatch(a ->
                                a.getAuthority().equals("ROLE_ADMIN"))
        );
    }

    @Test
    void loadUserByUsername_shouldUseUserRoleWhenRoleIsNull() {

        user.setRole(null);

        when(userRepository.findByEmail("manahil@example.com"))
                .thenReturn(Optional.of(user));

        UserDetails result =
                userDetailsService.loadUserByUsername(
                        "manahil@example.com"
                );

        assertTrue(
                result.getAuthorities()
                        .stream()
                        .anyMatch(a ->
                                a.getAuthority().equals("ROLE_USER"))
        );
    }
}