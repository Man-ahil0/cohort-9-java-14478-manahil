package com.example.cohort_9_java_14478_manahil.service;
import org.mockito.ArgumentCaptor;
import com.example.cohort_9_java_14478_manahil.dto.ChangePasswordRequest;
import com.example.cohort_9_java_14478_manahil.dto.UserDTO;
import com.example.cohort_9_java_14478_manahil.dto.UserResponseDTO;
import com.example.cohort_9_java_14478_manahil.entity.User;
import com.example.cohort_9_java_14478_manahil.exception.UserNotFoundException;
import com.example.cohort_9_java_14478_manahil.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

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

        userDTO = new UserDTO();
        userDTO.setFirstName("Manahil");
        userDTO.setLastName("Waheed");
        userDTO.setEmail("manahil@example.com");
        userDTO.setPhoneNumber("03001234567");
        userDTO.setPassword("password123");
        userDTO.setRole("USER");
    }
    @Test
    void createUser_shouldCreateAndReturnUser() {

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User savedUser = invocation.getArgument(0);
                    savedUser.setId(1L);
                    return savedUser;
                });

        UserResponseDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Manahil", result.getFirstName());
        assertEquals("Waheed", result.getLastName());
        assertEquals("manahil@example.com", result.getEmail());

        verify(passwordEncoder).encode("password123");

        verify(userRepository).save(argThat(savedUser ->
                "encodedPassword".equals(savedUser.getPassword())
        ));
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setFirstName("Ali");
        secondUser.setLastName("Khan");
        secondUser.setEmail("ali@example.com");
        secondUser.setPhoneNumber("03111234567");
        secondUser.setPassword("encodedPassword");
        secondUser.setRole("USER");

        when(userRepository.findAll())
                .thenReturn(List.of(user, secondUser));

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Manahil", result.get(0).getFirstName());
        assertEquals("Ali", result.get(1).getFirstName());

        verify(userRepository).findAll();
    }

    @Test
    void getUserById_shouldReturnUser() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Manahil", result.getFirstName());
        assertEquals("manahil@example.com", result.getEmail());

        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(1L)
        );

        verify(userRepository).findById(1L);
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponseDTO result =
                userService.updateUser(1L, userDTO);

        assertNotNull(result);
        assertEquals("Manahil", result.getFirstName());
        assertEquals("Waheed", result.getLastName());
        assertEquals("manahil@example.com", result.getEmail());

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).findById(1L);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(userCaptor.capture());

        assertEquals(
                "encodedPassword",
                userCaptor.getValue().getPassword()
        );

    }

    @Test
    void updateUser_shouldUpdateWithoutPasswordWhenPasswordIsEmpty() {

        userDTO.setPassword("");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponseDTO result =
                userService.updateUser(1L, userDTO);

        assertNotNull(result);

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);

        verify(passwordEncoder, never())
                .encode(anyString());
    }

    @Test
    void updateUser_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.updateUser(1L, userDTO)
        );

        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserByEmail_shouldReturnUser() {

        when(userRepository.findByEmail("manahil@example.com"))
                .thenReturn(Optional.of(user));

        UserResponseDTO result =
                userService.getUserByEmail("manahil@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Manahil", result.getFirstName());
        assertEquals("manahil@example.com", result.getEmail());
        assertEquals("USER", result.getRole());

        verify(userRepository)
                .findByEmail("manahil@example.com");
    }

    @Test
    void getUserByEmail_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserByEmail("unknown@example.com")
        );

        verify(userRepository)
                .findByEmail("unknown@example.com");
    }

    @Test
    void deleteUser_shouldDeleteUser() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.deleteUser(1L)
        );

        verify(userRepository).findById(1L);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void changePassword_shouldChangePasswordSuccessfully() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setCurrentPassword("oldPassword");
        request.setNewPassword("newPassword");

        when(userRepository.findByEmail("manahil@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "oldPassword",
                "encodedPassword"
        )).thenReturn(true);

        when(passwordEncoder.encode("newPassword"))
                .thenReturn("newEncodedPassword");

        userService.changePassword(
                "manahil@example.com",
                request
        );

        assertEquals(
                "newEncodedPassword",
                user.getPassword()
        );

        verify(userRepository)
                .findByEmail("manahil@example.com");

        verify(passwordEncoder)
                .matches("oldPassword", "encodedPassword");

        verify(passwordEncoder)
                .encode("newPassword");

        verify(userRepository)
                .save(user);
    }

    @Test
    void changePassword_shouldThrowExceptionWhenCurrentPasswordIsIncorrect() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setCurrentPassword("wrongPassword");
        request.setNewPassword("newPassword");

        when(userRepository.findByEmail("manahil@example.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrongPassword",
                "encodedPassword"
        )).thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.changePassword(
                        "manahil@example.com",
                        request
                )
        );

        verify(passwordEncoder)
                .matches("wrongPassword", "encodedPassword");

        verify(passwordEncoder, never())
                .encode("newPassword");

        verify(userRepository, never())
                .save(any(User.class));
    }

    @Test
    void changePassword_shouldThrowExceptionWhenUserNotFound() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setCurrentPassword("oldPassword");
        request.setNewPassword("newPassword");

        when(userRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.changePassword(
                        "unknown@example.com",
                        request
                )
        );

        verify(userRepository)
                .findByEmail("unknown@example.com");

        verify(passwordEncoder, never())
                .matches(anyString(), anyString());

        verify(userRepository, never())
                .save(any(User.class));
    }
}