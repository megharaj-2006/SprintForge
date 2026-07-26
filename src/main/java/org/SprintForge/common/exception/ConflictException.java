package org.SprintForge.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message, ErrorCode.CONFLICT);
    }

    public ConflictException(String message, ErrorCode errorCode) {
        super(HttpStatus.CONFLICT, message, errorCode);
    }

    public ConflictException(String message, Throwable cause) {
        super(HttpStatus.CONFLICT, message, ErrorCode.CONFLICT, cause);
    }

    public ConflictException(String message, ErrorCode errorCode, Throwable cause) {
        super(HttpStatus.CONFLICT, message, errorCode, cause);
    }
}
