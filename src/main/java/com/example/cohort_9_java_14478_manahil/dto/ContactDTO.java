package com.example.cohort_9_java_14478_manahil.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "firstName",
        "lastName",
        "email",
        "phoneNumber",
        "company",
        "jobTitle"
})

@Getter
@Setter
public class ContactDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{11}$", message = "Phone number must contain exactly 11 digits")
    private String phoneNumber;

    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String company;

    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    private String jobTitle;
}