package com.example.cohort_9_java_14478_manahil.controller;

import com.example.cohort_9_java_14478_manahil.dto.ContactDTO;
import com.example.cohort_9_java_14478_manahil.security.CustomUserDetailsService;
import com.example.cohort_9_java_14478_manahil.security.JwtService;
import com.example.cohort_9_java_14478_manahil.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContactService contactService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;


    // =========================================
    // CREATE CONTACT
    // =========================================

    @Test
    void shouldCreateContact() throws Exception {

        ContactDTO dto = new ContactDTO();

        dto.setId(1L);
        dto.setFirstName("Manahil");
        dto.setLastName("Waheed");
        dto.setEmail("manahil@gmail.com");
        dto.setPhoneNumber("03001234567");
        dto.setCompany("OpenAI");
        dto.setJobTitle("Java Developer");

        when(contactService.createContact(any(ContactDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(
                        post("/api/contacts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Manahil"))
                .andExpect(jsonPath("$.email").value("manahil@gmail.com"));
    }


    // =========================================
    // GET ALL CONTACTS
    // =========================================

    @Test
    void shouldGetAllContacts() throws Exception {

        ContactDTO dto = new ContactDTO();

        dto.setId(1L);
        dto.setFirstName("Manahil");
        dto.setLastName("Waheed");
        dto.setEmail("manahil@gmail.com");
        dto.setPhoneNumber("03001234567");

        Page<ContactDTO> page = new PageImpl<>(
                List.of(dto),
                PageRequest.of(0, 5),
                1
        );

        when(contactService.getAllContacts(0, 5))
                .thenReturn(page);

        mockMvc.perform(
                        get("/api/contacts")
                                .param("page", "0")
                                .param("size", "5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName")
                        .value("Manahil"))
                .andExpect(jsonPath("$.content[0].lastName")
                        .value("Waheed"));
    }


    // =========================================
    // GET CONTACT BY ID
    // =========================================

    @Test
    void shouldGetContactById() throws Exception {

        ContactDTO dto = new ContactDTO();

        dto.setId(1L);
        dto.setFirstName("Manahil");
        dto.setLastName("Waheed");

        when(contactService.getContactById(1L))
                .thenReturn(dto);

        mockMvc.perform(
                        get("/api/contacts/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName")
                        .value("Manahil"));
    }


    // =========================================
    // UPDATE CONTACT
    // =========================================

    @Test
    void shouldUpdateContact() throws Exception {

        ContactDTO dto = new ContactDTO();

        dto.setId(1L);
        dto.setFirstName("Updated");
        dto.setLastName("Waheed");
        dto.setEmail("manahil@gmail.com");
        dto.setPhoneNumber("03001234567");

        when(contactService.updateContact(
                eq(1L),
                any(ContactDTO.class)
        )).thenReturn(dto);

        mockMvc.perform(
                        put("/api/contacts/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName")
                        .value("Updated"));
    }


    // =========================================
    // DELETE CONTACT
    // =========================================

    @Test
    void shouldDeleteContact() throws Exception {

        doNothing()
                .when(contactService)
                .deleteContact(1L);

        mockMvc.perform(
                        delete("/api/contacts/1")
                )
                .andExpect(status().isOk());
    }
}