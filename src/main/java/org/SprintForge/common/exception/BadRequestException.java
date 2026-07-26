package org.SprintForge.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message, ErrorCode.BAD_REQUEST);
    }

    public BadRequestException(String message, ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, message, errorCode);
    }

    public BadRequestException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, ErrorCode.BAD_REQUEST, cause);
    }

    public BadRequestException(String message, ErrorCode errorCode, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, errorCode, cause);
    }
}
