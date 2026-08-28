package com.example.cohort_9_java_14478_manahil.controller;

import com.example.cohort_9_java_14478_manahil.dto.ContactDTO;
import com.example.cohort_9_java_14478_manahil.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;


import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // Create Contact
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@Valid @RequestBody ContactDTO contactDTO) {

        ContactDTO savedContact = contactService.createContact(contactDTO);

        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    // Get All Contacts
    @GetMapping
    public ResponseEntity<Page<ContactDTO>> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(contactService.getAllContacts(page, size));
    }

    // Get Contact By ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {

        return ResponseEntity.ok(contactService.getContactById(id));
    }

    // Update Contact
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactDTO contactDTO) {

        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);

        return ResponseEntity.ok(updatedContact);
    }

    // Delete Contact
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {

        contactService.deleteContact(id);

        return ResponseEntity.ok("Contact deleted successfully");
    }

    // Search by First Name
    @GetMapping("/search/firstname")
    public ResponseEntity<List<ContactDTO>> searchByFirstName(
            @RequestParam String firstName) {

        return ResponseEntity.ok(contactService.searchByFirstName(firstName));
    }


    // Search by Last Name
    @GetMapping("/search/lastname")
    public ResponseEntity<List<ContactDTO>> searchByLastName(

            @RequestParam String lastName) {

        return ResponseEntity.ok(contactService.searchByLastName(lastName));
    }


    // Search by Email
    @GetMapping("/search/email")
    public ResponseEntity<List<ContactDTO>> searchByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(contactService.searchByEmail(email));
    }


    // Search by Company
    @GetMapping("/search/company")
    public ResponseEntity<List<ContactDTO>> searchByCompany(

            @RequestParam String company) {

        return ResponseEntity.ok(contactService.searchByCompany(company));
    }


    // Search by Job Title
    @GetMapping("/search/jobtitle")
    public ResponseEntity<List<ContactDTO>> searchByJobTitle(

            @RequestParam String jobTitle) {

        return ResponseEntity.ok(contactService.searchByJobTitle(jobTitle));
    }

    // Filter by Company
    @GetMapping("/filter/company")
    public ResponseEntity<List<ContactDTO>> filterByCompany(
            @RequestParam String company) {

        return ResponseEntity.ok(contactService.filterByCompany(company));
    }
    // Filter by Job Title
    @GetMapping("/filter/jobtitle")
    public ResponseEntity<List<ContactDTO>> filterByJobTitle(

            @RequestParam String jobTitle) {

        return ResponseEntity.ok(contactService.filterByJobTitle(jobTitle));
    }

}