package com.example.cohort_9_java_14478_manahil.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.cohort_9_java_14478_manahil.dto.ContactDTO;
import com.example.cohort_9_java_14478_manahil.entity.Contact;
import com.example.cohort_9_java_14478_manahil.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.cohort_9_java_14478_manahil.exception.ContactNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    private Contact contact;
    private ContactDTO contactDTO;

    @BeforeEach
    void setUp() {

        contact = new Contact();
        contact.setId(1L);
        contact.setFirstName("Manahil");
        contact.setLastName("Waheed");
        contact.setEmail("manahil@gmail.com");
        contact.setPhoneNumber("03001234567");
        contact.setCompany("OpenAI");
        contact.setJobTitle("Java Developer");

        contactDTO = new ContactDTO();
        contactDTO.setFirstName("Manahil");
        contactDTO.setLastName("Waheed");
        contactDTO.setEmail("manahil@gmail.com");
        contactDTO.setPhoneNumber("03001234567");
        contactDTO.setCompany("OpenAI");
        contactDTO.setJobTitle("Java Developer");
    }

    @Test
    void shouldCreateContact() {

        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactDTO saved = contactService.createContact(contactDTO);

        assertNotNull(saved);
        assertEquals("Manahil", saved.getFirstName());
        assertEquals("Waheed", saved.getLastName());

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void shouldGetAllContacts() {

        Pageable pageable = PageRequest.of(0, 5);

        Page<Contact> contactPage =
                new PageImpl<>(List.of(contact), pageable, 1);

        when(contactRepository.findAll(pageable))
                .thenReturn(contactPage);

        Page<ContactDTO> contacts =
                contactService.getAllContacts(0, 5);

        assertNotNull(contacts);
        assertEquals(1, contacts.getTotalElements());
        assertEquals("Manahil",
                contacts.getContent().get(0).getFirstName());

        verify(contactRepository, times(1))
                .findAll(pageable);
    }

    @Test
    void shouldGetContactById() {

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        ContactDTO found = contactService.getContactById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("Manahil", found.getFirstName());

        verify(contactRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateContact() {

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        contactDTO.setFirstName("Updated");

        ContactDTO updated = contactService.updateContact(1L, contactDTO);

        assertNotNull(updated);
        assertEquals("Updated", updated.getFirstName());

        verify(contactRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void shouldDeleteContact() {

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        contactService.deleteContact(1L);

        verify(contactRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).delete(contact);
    }

    @Test
    void shouldThrowExceptionWhenContactNotFound() {

        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> {
            contactService.getContactById(99L);
        });

        verify(contactRepository, times(1)).findById(99L);
    }
}