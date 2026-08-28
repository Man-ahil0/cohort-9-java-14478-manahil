package com.example.cohort_9_java_14478_manahil.controller;

import com.example.cohort_9_java_14478_manahil.dto.AuthResponse;
import com.example.cohort_9_java_14478_manahil.dto.LoginRequest;
import com.example.cohort_9_java_14478_manahil.dto.RegisterRequest;
import com.example.cohort_9_java_14478_manahil.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}