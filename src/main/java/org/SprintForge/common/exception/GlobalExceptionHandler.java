package org.SprintForge.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final Set<String> SENSITIVE_FIELDS = Set.of("password", "token", "secret", "credential", "apikey", "currentpassword", "newpassword");

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.warn("Business rule violation at {}: {}", request.getRequestURI(), ex.getMessage());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Business Rule Violation")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code(ErrorCode.BAD_REQUEST.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        if (ex.getStatus().is5xxServerError()) {
            log.error("API error [{}] at {}: {}", ex.getStatus(), request.getRequestURI(), ex.getMessage(), ex);
        } else {
            log.warn("API exception [{}] at {}: {}", ex.getStatus(), request.getRequestURI(), ex.getMessage());
        }
        String code = ex.getErrorCode() != null ? ex.getErrorCode().getValue() : getFallbackErrorCode(ex.getStatus());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(ex.getStatus().value())
                .error(getErrorTitle(ex))
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code(code)
                .build();
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(ValidationException ex, HttpServletRequest request) {
        log.warn("Validation failure at {}: {}", request.getRequestURI(), ex.getMessage());
        List<FieldErrorResponse> sanitizedErrors = sanitizeErrors(ex.getErrors());
        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .status(ex.getStatus().value())
                .error("Validation Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code(ErrorCode.INVALID_INPUT.getValue())
                .errors(sanitizedErrors)
                .validationErrors(sanitizedErrors.stream().collect(
                        Collectors.toMap(
                                FieldErrorResponse::getField,
                                FieldErrorResponse::getMessage,
                                (a, b) -> a
                        )
                ))
                .build();
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("Validation failure on request body at {}", request.getRequestURI());
        List<FieldErrorResponse> errors = new ArrayList<>();
        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = error instanceof FieldError fe ? fe.getField() : error.getObjectName();
            Object rejectedValue = error instanceof FieldError fe ? sanitizeRejectedValue(fieldName, fe.getRejectedValue()) : null;
            String errorMessage = error.getDefaultMessage();

            errors.add(FieldErrorResponse.builder()
                    .field(fieldName)
                    .rejectedValue(rejectedValue)
                    .message(errorMessage)
                    .build());

            validationErrors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .message("Input validation failed")
                .path(request.getRequestURI())
                .code(ErrorCode.INVALID_INPUT.getValue())
                .errors(errors)
                .validationErrors(validationErrors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.warn("Constraint violation at {}: {}", request.getRequestURI(), ex.getMessage());
        List<FieldErrorResponse> errors = new ArrayList<>();
        Map<String, String> validationErrors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            Object rejectedValue = sanitizeRejectedValue(fieldName, violation.getInvalidValue());
            String message = violation.getMessage();

            errors.add(FieldErrorResponse.builder()
                    .field(fieldName)
                    .rejectedValue(rejectedValue)
                    .message(message)
                    .build());

            validationErrors.put(fieldName, message);
        });

        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .message("Validation failed")
                .path(request.getRequestURI())
                .code(ErrorCode.INVALID_INPUT.getValue())
                .errors(errors)
                .validationErrors(validationErrors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("Malformed JSON payload at {}", request.getRequestURI());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Malformed JSON Request")
                .message("Required request body is missing or malformed")
                .path(request.getRequestURI())
                .code(ErrorCode.INVALID_INPUT.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = String.format("Parameter '%s' should be of type '%s'", ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Type Mismatch")
                .message(message)
                .path(request.getRequestURI())
                .code(ErrorCode.INVALID_INPUT.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.warn("Missing request parameter '{}' at {}", ex.getParameterName(), request.getRequestURI());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Missing Parameter")
                .message(String.format("Required request parameter '%s' is missing", ex.getParameterName()))
                .path(request.getRequestURI())
                .code(ErrorCode.BAD_REQUEST.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("Method {} not supported at {}", ex.getMethod(), request.getRequestURI());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .error("Method Not Allowed")
                .message(String.format("HTTP method '%s' is not supported for this endpoint", ex.getMethod()))
                .path(request.getRequestURI())
                .code(ErrorCode.BAD_REQUEST.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        log.warn("Content type {} not supported at {}", ex.getContentType(), request.getRequestURI());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .error("Unsupported Media Type")
                .message("Request content type is not supported")
                .path(request.getRequestURI())
                .code(ErrorCode.BAD_REQUEST.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        log.warn("Upload size exceeded at {}", request.getRequestURI());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .error("Payload Too Large")
                .message("The uploaded file exceeds the maximum permitted payload size")
                .path(request.getRequestURI())
                .code(ErrorCode.BAD_REQUEST.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error("Data integrity violation at {}: {}", request.getRequestURI(), ex.getMessage());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error("Data Conflict")
                .message("A database integrity constraint was violated. The record may already exist or is referenced by other data.")
                .path(request.getRequestURI())
                .code(ErrorCode.CONFLICT.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiErrorResponse> handleDisabledException(DisabledException ex, HttpServletRequest request) {
        log.warn("Access attempt on disabled account at {}", request.getRequestURI());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error("Account Disabled")
                .message("User account is disabled. Please contact administrator.")
                .path(request.getRequestURI())
                .code(ErrorCode.PERMISSION_DENIED.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Access denied at {}: {}", request.getRequestURI(), ex.getMessage());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error("Forbidden Access")
                .message("You do not have permission to access this resource")
                .path(request.getRequestURI())
                .code(ErrorCode.PERMISSION_DENIED.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        String code = ErrorCode.UNAUTHORIZED.getValue();
        if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("expired")) {
            code = ErrorCode.TOKEN_EXPIRED.getValue();
        }
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Unauthorized")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code(code)
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        String safeMessage = "local".equalsIgnoreCase(activeProfile) || "dev".equalsIgnoreCase(activeProfile)
                ? ex.getMessage()
                : "An unexpected error occurred. Please contact system support.";

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(safeMessage)
                .path(request.getRequestURI())
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<FieldErrorResponse> sanitizeErrors(List<FieldErrorResponse> errors) {
        if (errors == null) return List.of();
        return errors.stream().map(e -> {
            if (isSensitiveField(e.getField())) {
                return FieldErrorResponse.builder()
                        .field(e.getField())
                        .rejectedValue(null)
                        .message(e.getMessage())
                        .build();
            }
            return e;
        }).collect(Collectors.toList());
    }

    private Object sanitizeRejectedValue(String fieldName, Object value) {
        if (isSensitiveField(fieldName)) {
            return null;
        }
        return value;
    }

    private boolean isSensitiveField(String fieldName) {
        if (fieldName == null) return false;
        String lower = fieldName.toLowerCase();
        return SENSITIVE_FIELDS.stream().anyMatch(lower::contains);
    }

    private String getErrorTitle(ApiException ex) {
        return switch (ex.getClass().getSimpleName()) {
            case "ResourceNotFoundException", "UserNotFoundException" -> "Resource Not Found";
            case "DuplicateResourceException" -> "Duplicate Resource";
            case "InvalidOperationException" -> "Invalid Operation";
            case "UnauthorizedException" -> "Unauthorized";
            case "ForbiddenException" -> "Forbidden Access";
            case "AuthException" -> "Authentication Error";
            case "UsernameAlreadyExistsException" -> "Username Conflict";
            case "InvalidAvatarException" -> "Invalid Avatar";
            case "UserAccountDeactivatedException" -> "Account Deactivated";
            default -> ex.getStatus().getReasonPhrase();
        };
    }

    private String getFallbackErrorCode(HttpStatus status) {
        return switch (status) {
            case NOT_FOUND -> ErrorCode.RESOURCE_NOT_FOUND.getValue();
            case CONFLICT -> ErrorCode.CONFLICT.getValue();
            case UNAUTHORIZED -> ErrorCode.UNAUTHORIZED.getValue();
            case FORBIDDEN -> ErrorCode.FORBIDDEN.getValue();
            case BAD_REQUEST -> ErrorCode.BAD_REQUEST.getValue();
            default -> ErrorCode.INTERNAL_SERVER_ERROR.getValue();
        };
    }
}