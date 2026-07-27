package org.SprintForge.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends ApiException {

    public BusinessRuleException(String message) {
        super(HttpStatus.UNPROCESSABLE_CONTENT, message, ErrorCode.BAD_REQUEST);
    }

    public BusinessRuleException(String message, ErrorCode errorCode) {
        super(HttpStatus.UNPROCESSABLE_CONTENT, message, errorCode);
    }

    public BusinessRuleException(HttpStatus status, String message, ErrorCode errorCode) {
        super(status, message, errorCode);
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(HttpStatus.UNPROCESSABLE_CONTENT, message, ErrorCode.BAD_REQUEST, cause);
    }

    public BusinessRuleException(String message, ErrorCode errorCode, Throwable cause) {
        super(HttpStatus.UNPROCESSABLE_CONTENT, message, errorCode, cause);
    }
}
