package org.SprintForge.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
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
        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .status(ex.getStatus().value())
                .error("Validation Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code(ErrorCode.INVALID_INPUT.getValue())
                .errors(ex.getErrors())
                .validationErrors(ex.getErrors().stream().collect(
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
        List<FieldErrorResponse> errors = new ArrayList<>();
        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = error instanceof FieldError fe ? fe.getField() : error.getObjectName();
            Object rejectedValue = error instanceof FieldError fe ? fe.getRejectedValue() : null;
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
        List<FieldErrorResponse> errors = new ArrayList<>();
        Map<String, String> validationErrors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            Object rejectedValue = violation.getInvalidValue();
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
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
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
