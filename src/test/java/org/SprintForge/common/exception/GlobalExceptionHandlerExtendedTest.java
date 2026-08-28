package org.SprintForge.common.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerExtendedTest {

    private GlobalExceptionHandler exceptionHandler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/test");
    }

    @Test
    void handleDataIntegrityViolationException_ShouldReturn409Conflict() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Unique constraint violation", new RuntimeException("duplicate key"));
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleDataIntegrityViolationException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Data Conflict", response.getBody().getError());
        assertEquals(ErrorCode.CONFLICT.getValue(), response.getBody().getCode());
    }

    @Test
    void handleDisabledException_ShouldReturn403Forbidden() {
        DisabledException ex = new DisabledException("User account is disabled");
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleDisabledException(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Account Disabled", response.getBody().getError());
        assertEquals(ErrorCode.PERMISSION_DENIED.getValue(), response.getBody().getCode());
    }

    @Test
    void handleHttpRequestMethodNotSupported_ShouldReturn405MethodNotAllowed() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("DELETE");
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleHttpRequestMethodNotSupported(ex, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Method Not Allowed", response.getBody().getError());
    }

    @Test
    void handleHttpMediaTypeNotSupported_ShouldReturn415UnsupportedMediaType() {
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException("application/xml");
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleHttpMediaTypeNotSupported(ex, request);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unsupported Media Type", response.getBody().getError());
    }

    @Test
    void handleMissingServletRequestParameter_ShouldReturn400BadRequest() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("workspaceId", "String");
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleMissingServletRequestParameter(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Missing Parameter", response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains("workspaceId"));
    }

    @Test
    void handleMaxUploadSizeExceeded_ShouldReturn413PayloadTooLarge() {
        MaxUploadSizeExceededException ex = new MaxUploadSizeExceededException(50000000);
        ResponseEntity<ApiErrorResponse> response = exceptionHandler.handleMaxUploadSizeExceeded(ex, request);

        assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Payload Too Large", response.getBody().getError());
    }

    @Test
    void handleValidationException_ShouldMaskSensitiveFieldValues() {
        List<FieldErrorResponse> errors = List.of(
                FieldErrorResponse.builder().field("password").rejectedValue("SuperSecret123!").message("Too weak").build(),
                FieldErrorResponse.builder().field("username").rejectedValue("john").message("Invalid format").build()
        );
        ValidationException ex = new ValidationException("Validation failed", errors);

        ResponseEntity<ValidationErrorResponse> response = exceptionHandler.handleValidationException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        // Verify password rejectedValue is sanitized to null
        FieldErrorResponse passwordError = response.getBody().getErrors().stream()
                .filter(e -> "password".equals(e.getField()))
                .findFirst().orElseThrow();
        assertNull(passwordError.getRejectedValue());

        // Non-sensitive field value is preserved
        FieldErrorResponse usernameError = response.getBody().getErrors().stream()
                .filter(e -> "username".equals(e.getField()))
                .findFirst().orElseThrow();
        assertEquals("john", usernameError.getRejectedValue());
    }
}
