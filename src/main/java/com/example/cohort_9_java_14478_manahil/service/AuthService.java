package com.example.cohort_9_java_14478_manahil.service;
import com.example.cohort_9_java_14478_manahil.dto.RegisterRequest;
import com.example.cohort_9_java_14478_manahil.dto.AuthResponse;
import com.example.cohort_9_java_14478_manahil.dto.LoginRequest;
import com.example.cohort_9_java_14478_manahil.entity.User;
import com.example.cohort_9_java_14478_manahil.repository.UserRepository;
import com.example.cohort_9_java_14478_manahil.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // Register
    // Register
    public AuthResponse register(RegisterRequest request) {

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Always assign USER role
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(savedUser.getEmail())
                        .password(savedUser.getPassword())
                        .roles(savedUser.getRole())
                        .build();

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }

    // Login
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole() == null ? "USER" : user.getRole())
                        .build();

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}