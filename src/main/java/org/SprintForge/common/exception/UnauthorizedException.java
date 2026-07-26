package org.SprintForge.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message, ErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, ErrorCode errorCode) {
        super(HttpStatus.UNAUTHORIZED, message, errorCode);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, ErrorCode.UNAUTHORIZED, cause);
    }

    public UnauthorizedException(String message, ErrorCode errorCode, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, message, errorCode, cause);
    }
}
