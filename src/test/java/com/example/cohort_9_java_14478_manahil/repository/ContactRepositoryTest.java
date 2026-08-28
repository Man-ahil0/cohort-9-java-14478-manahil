package com.example.cohort_9_java_14478_manahil.repository;

import com.example.cohort_9_java_14478_manahil.entity.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    private Contact contact1;
    private Contact contact2;

    @BeforeEach
    void setUp() {

        contactRepository.deleteAll();

        contact1 = new Contact();
        contact1.setFirstName("Manahil");
        contact1.setLastName("Waheed");
        contact1.setEmail("manahil@gmail.com");
        contact1.setPhoneNumber("03001234567");
        contact1.setCompany("OpenAI");
        contact1.setJobTitle("Java Developer");

        contact2 = new Contact();
        contact2.setFirstName("Hooria");
        contact2.setLastName("Khan");
        contact2.setEmail("hooria@gmail.com");
        contact2.setPhoneNumber("03111234567");
        contact2.setCompany("Microsoft");
        contact2.setJobTitle("Software Engineer");

        contactRepository.save(contact1);
        contactRepository.save(contact2);
    }

    @Test
    void shouldFindByFirstNameContainingIgnoreCase() {

        List<Contact> contacts =
                contactRepository.findByFirstNameContainingIgnoreCase("mana");

        assertEquals(1, contacts.size());
        assertEquals("Manahil", contacts.get(0).getFirstName());
    }

    @Test
    void shouldFindByLastNameContainingIgnoreCase() {

        List<Contact> contacts =
                contactRepository.findByLastNameContainingIgnoreCase("WAHEED");

        assertEquals(1, contacts.size());
        assertEquals("Waheed", contacts.get(0).getLastName());
    }

    @Test
    void shouldFindByEmailContainingIgnoreCase() {

        List<Contact> contacts =
                contactRepository.findByEmailContainingIgnoreCase("MANAHIL");

        assertEquals(1, contacts.size());
        assertEquals("manahil@gmail.com", contacts.get(0).getEmail());
    }

    @Test
    void shouldFindByCompanyContainingIgnoreCase() {

        List<Contact> contacts =
                contactRepository.findByCompanyContainingIgnoreCase("open");

        assertEquals(1, contacts.size());
        assertEquals("OpenAI", contacts.get(0).getCompany());
    }

    @Test
    void shouldFindByJobTitleContainingIgnoreCase() {

        List<Contact> contacts =
                contactRepository.findByJobTitleContainingIgnoreCase("JAVA");

        assertEquals(1, contacts.size());
        assertEquals("Java Developer", contacts.get(0).getJobTitle());
    }

    @Test
    void shouldFindByCompany() {

        List<Contact> contacts =
                contactRepository.findByCompany("OpenAI");

        assertEquals(1, contacts.size());
        assertEquals("Manahil", contacts.get(0).getFirstName());
    }

    @Test
    void shouldFindByJobTitle() {

        List<Contact> contacts =
                contactRepository.findByJobTitle("Software Engineer");

        assertEquals(1, contacts.size());
        assertEquals("Hooria", contacts.get(0).getFirstName());
    }
}