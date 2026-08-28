package com.example.cohort_9_java_14478_manahil.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.cohort_9_java_14478_manahil.dto.ChangePasswordRequest;
import com.example.cohort_9_java_14478_manahil.dto.UserDTO;
import com.example.cohort_9_java_14478_manahil.dto.UserResponseDTO;
import com.example.cohort_9_java_14478_manahil.service.UserService;
import com.example.cohort_9_java_14478_manahil.security.CustomUserDetailsService;
import com.example.cohort_9_java_14478_manahil.security.JwtService;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.List;import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private UserDTO userDTO;
    private UserResponseDTO userResponse;


    @BeforeEach
    void setUp() {

        userDTO = new UserDTO();

        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setPhoneNumber("03001234567");
        userDTO.setPassword("Password123");

        userResponse = new UserResponseDTO(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "03001234567",
                "USER"
        );
    }


    @Test
    void createUser_shouldReturnCreated() throws Exception {

        UserDTO userDTO = new UserDTO(
                "John",
                "Doe",
                "john@example.com",
                "03001234567",
                "Password123",
                null
        );

        UserResponseDTO responseDTO = new UserResponseDTO(
                1L,
                "John",
                "Doe",
                "john@example.com",
                "03001234567",
                "USER"
        );

        when(userService.createUser(any(UserDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(
                        post("/api/users")
                                .with(user("test@example.com").roles("USER"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService).createUser(any(UserDTO.class));
    }

    @Test
    void getAllUsers_shouldReturnOk() throws Exception {

        when(userService.getAllUsers())
                .thenReturn(List.of(userResponse));

        mockMvc.perform(
                        get("/api/users")
                                .with(user("john@example.com")
                                        .roles("USER"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));

        verify(userService).getAllUsers();
    }


    @Test
    void getUserById_shouldReturnOk() throws Exception {

        when(userService.getUserById(1L))
                .thenReturn(userResponse);

        mockMvc.perform(
                        get("/api/users/1")
                                .with(user("john@example.com")
                                        .roles("USER"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService).getUserById(1L);
    }


    @Test
    void updateUser_shouldReturnOk() throws Exception {

        when(userService.updateUser(
                eq(1L),
                any(UserDTO.class)
        )).thenReturn(userResponse);

        mockMvc.perform(
                        put("/api/users/1")
                                .with(csrf())
                                .with(user("john@example.com")
                                        .roles("USER"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService).updateUser(
                eq(1L),
                any(UserDTO.class)
        );
    }


    @Test
    void deleteUser_shouldReturnOk() throws Exception {

        doNothing().when(userService)
                .deleteUser(1L);

        mockMvc.perform(
                        delete("/api/users/1")
                                .with(csrf())
                                .with(user("john@example.com")
                                        .roles("USER"))
                )
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("User deleted successfully"));

        verify(userService).deleteUser(1L);
    }


    @Test
    void getCurrentUser_shouldReturnOk() throws Exception {

        when(userService.getUserByEmail("john@example.com"))
                .thenReturn(userResponse);

        mockMvc.perform(
                        get("/api/users/me")
                                .with(user("john@example.com")
                                        .roles("USER"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email")
                        .value("john@example.com"))
                .andExpect(jsonPath("$.phoneNumber")
                        .value("03001234567"));

        verify(userService)
                .getUserByEmail("john@example.com");
    }


    @Test
    void changePassword_shouldReturnOk() throws Exception {

        ChangePasswordRequest request = new ChangePasswordRequest();

        request.setCurrentPassword("OldPassword123");
        request.setNewPassword("NewPassword123");

        doNothing().when(userService)
                .changePassword(
                        eq("john@example.com"),
                        any(ChangePasswordRequest.class)
                );

        mockMvc.perform(
                        put("/api/users/change-password")
                                .with(csrf())
                                .with(user("john@example.com")
                                        .roles("USER"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Password changed successfully."));

        verify(userService).changePassword(
                eq("john@example.com"),
                any(ChangePasswordRequest.class)
        );
    }
}