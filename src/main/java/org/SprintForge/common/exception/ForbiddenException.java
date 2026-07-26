package org.SprintForge.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApiException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message, ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(String message, ErrorCode errorCode) {
        super(HttpStatus.FORBIDDEN, message, errorCode);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(HttpStatus.FORBIDDEN, message, ErrorCode.FORBIDDEN, cause);
    }

    public ForbiddenException(String message, ErrorCode errorCode, Throwable cause) {
        super(HttpStatus.FORBIDDEN, message, errorCode, cause);
    }
}
