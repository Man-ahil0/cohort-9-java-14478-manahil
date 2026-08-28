package com.example.cohort_9_java_14478_manahil.repository;

import com.example.cohort_9_java_14478_manahil.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}