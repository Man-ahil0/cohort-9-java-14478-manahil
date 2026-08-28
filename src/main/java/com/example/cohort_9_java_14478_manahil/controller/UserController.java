package com.example.cohort_9_java_14478_manahil.controller;

import com.example.cohort_9_java_14478_manahil.dto.UserDTO;
import com.example.cohort_9_java_14478_manahil.dto.UserResponseDTO;
import com.example.cohort_9_java_14478_manahil.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.cohort_9_java_14478_manahil.dto.ChangePasswordRequest;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserDTO userDTO) {

        return new ResponseEntity<>(
                userService.createUser(userDTO),
                HttpStatus.CREATED
        );
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(
                userService.updateUser(id, userDTO)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");
    }
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(
            Authentication authentication) {

        return ResponseEntity.ok(
                userService.getUserByEmail(authentication.getName())
        );
    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(authentication.getName(), request);

        return ResponseEntity.ok("Password changed successfully.");
    }
}