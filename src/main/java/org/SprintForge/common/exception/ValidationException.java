package org.SprintForge.common.exception;

import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.List;

public class ValidationException extends ApiException {
    private final List<FieldErrorResponse> errors;

    public ValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message, ErrorCode.INVALID_INPUT);
        this.errors = Collections.emptyList();
    }

    public ValidationException(String message, List<FieldErrorResponse> errors) {
        super(HttpStatus.BAD_REQUEST, message, ErrorCode.INVALID_INPUT);
        this.errors = errors != null ? errors : Collections.emptyList();
    }

    public List<FieldErrorResponse> getErrors() {
        return errors;
    }
}
