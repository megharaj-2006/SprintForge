package org.SprintForge.modules.user.exception;

import org.springframework.http.HttpStatus;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ErrorCode;

public class UsernameAlreadyExistsException extends ConflictException {
    public UsernameAlreadyExistsException(String username) {
        super("Username is already taken: " + username, ErrorCode.USERNAME_ALREADY_EXISTS);
    }
}
