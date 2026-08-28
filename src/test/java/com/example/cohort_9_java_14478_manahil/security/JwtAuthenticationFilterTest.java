package com.example.cohort_9_java_14478_manahil.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {

        filter = new JwtAuthenticationFilter(
                jwtService,
                userDetailsService
        );

        userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername("manahil@example.com")
                        .password("password")
                        .roles("USER")
                        .build();

        SecurityContextHolder.clearContext();
    }

    @Test
    void filter_shouldContinueWhenAuthorizationHeaderIsMissing()
            throws ServletException, IOException {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        filter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(request, response);

        assertNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );
    }

    @Test
    void filter_shouldContinueWhenAuthorizationHeaderIsInvalid()
            throws ServletException, IOException {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Basic abc123"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        filter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(request, response);

        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void filter_shouldAuthenticateUserWithValidToken()
            throws ServletException, IOException {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer valid-token"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        when(jwtService.extractUsername("valid-token"))
                .thenReturn("manahil@example.com");

        when(userDetailsService.loadUserByUsername(
                "manahil@example.com"
        )).thenReturn(userDetails);

        when(jwtService.isTokenValid(
                "valid-token",
                userDetails
        )).thenReturn(true);

        filter.doFilter(
                request,
                response,
                filterChain
        );

        assertNotNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );

        assertEquals(
                "manahil@example.com",
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        );

        verify(jwtService)
                .extractUsername("valid-token");

        verify(jwtService)
                .isTokenValid(
                        "valid-token",
                        userDetails
                );

        verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    void filter_shouldNotAuthenticateUserWithInvalidToken()
            throws ServletException, IOException {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer invalid-token"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        when(jwtService.extractUsername("invalid-token"))
                .thenReturn("manahil@example.com");

        when(userDetailsService.loadUserByUsername(
                "manahil@example.com"
        )).thenReturn(userDetails);

        when(jwtService.isTokenValid(
                "invalid-token",
                userDetails
        )).thenReturn(false);

        filter.doFilter(
                request,
                response,
                filterChain
        );

        assertNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );

        verify(filterChain)
                .doFilter(request, response);
    }
}