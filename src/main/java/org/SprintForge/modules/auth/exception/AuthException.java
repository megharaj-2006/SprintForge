package org.SprintForge.modules.auth.exception;

import org.SprintForge.common.exception.BusinessException;

import org.SprintForge.common.exception.ErrorCode;
import org.SprintForge.common.exception.UnauthorizedException;

public class AuthException extends UnauthorizedException {
    public AuthException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}