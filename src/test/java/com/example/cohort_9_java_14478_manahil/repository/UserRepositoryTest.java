package com.example.cohort_9_java_14478_manahil.repository;

import com.example.cohort_9_java_14478_manahil.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User();

        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("testuser12345@gmail.com");
        user.setPhoneNumber("03001234567");
        user.setPassword("password123");
        user.setRole("USER");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveUserSuccessfully() {

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("testuser12345@gmail.com", savedUser.getEmail());
        assertEquals("Test", savedUser.getFirstName());
    }

    @Test
    void shouldFindUserByEmail() {

        userRepository.save(user);

        Optional<User> foundUser =
                userRepository.findByEmail("testuser12345@gmail.com");

        assertTrue(foundUser.isPresent());
        assertEquals(
                "testuser12345@gmail.com",
                foundUser.get().getEmail()
        );
    }

    @Test
    void shouldReturnEmptyWhenUserEmailDoesNotExist() {

        Optional<User> foundUser =
                userRepository.findByEmail("doesnotexist12345@gmail.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void shouldDeleteUserSuccessfully() {

        User savedUser = userRepository.save(user);

        Long userId = savedUser.getId();

        userRepository.deleteById(userId);

        Optional<User> deletedUser =
                userRepository.findById(userId);

        assertFalse(deletedUser.isPresent());
    }
}