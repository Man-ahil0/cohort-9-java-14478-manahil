package com.example.cohort_9_java_14478_manahil.repository;

import com.example.cohort_9_java_14478_manahil.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByFirstNameContainingIgnoreCase(String firstName);

    List<Contact> findByLastNameContainingIgnoreCase(String lastName);

    List<Contact> findByEmailContainingIgnoreCase(String email);

    List<Contact> findByCompanyContainingIgnoreCase(String company);

    List<Contact> findByJobTitleContainingIgnoreCase(String jobTitle);

    List<Contact> findByCompany(String company);

    List<Contact> findByJobTitle(String jobTitle);
}