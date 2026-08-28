package com.example.cohort_9_java_14478_manahil.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleContactNotFound_shouldReturnNotFound() {

        ContactNotFoundException exception =
                new ContactNotFoundException(
                        "Contact not found with ID: 1"
                );

        ResponseEntity<String> response =
                exceptionHandler.handleContactNotFound(exception);

        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

        assertEquals(
                "Contact not found with ID: 1",
                response.getBody()
        );
    }

    @Test
    void handleUserNotFound_shouldReturnNotFound() {

        UserNotFoundException exception =
                new UserNotFoundException(
                        "User not found with ID: 1"
                );

        ResponseEntity<String> response =
                exceptionHandler.handleUserNotFound(exception);

        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

        assertEquals(
                "User not found with ID: 1",
                response.getBody()
        );
    }

    @Test
    void handleIllegalArgument_shouldReturnBadRequest() {

        IllegalArgumentException exception =
                new IllegalArgumentException(
                        "Current password is incorrect"
                );

        ResponseEntity<String> response =
                exceptionHandler.handleIllegalArgument(exception);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertEquals(
                "Current password is incorrect",
                response.getBody()
        );
    }

    @Test
    void handleGeneralException_shouldReturnGenericMessage() {

        Exception exception =
                new Exception("Sensitive internal database error");

        ResponseEntity<String> response =
                exceptionHandler.handleGeneralException(exception);

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertEquals(
                "An unexpected error occurred",
                response.getBody()
        );

        assertFalse(
                response.getBody().contains("Sensitive internal database error")
        );
    }

    @Test
    void handleValidationErrors_shouldReturnBadRequestWithErrors() {

        MethodArgumentNotValidException exception =
                mock(MethodArgumentNotValidException.class);

        var bindingResult =
                mock(org.springframework.validation.BindingResult.class);

        var fieldError =
                new org.springframework.validation.FieldError(
                        "userDTO",
                        "email",
                        "Email is required"
                );

        when(exception.getBindingResult())
                .thenReturn(bindingResult);

        when(bindingResult.getFieldErrors())
                .thenReturn(java.util.List.of(fieldError));

        ResponseEntity<java.util.Map<String, String>> response =
                exceptionHandler.handleValidationErrors(exception);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                "Email is required",
                response.getBody().get("email")
        );
    }
}