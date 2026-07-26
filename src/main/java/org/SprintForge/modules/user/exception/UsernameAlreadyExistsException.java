package org.SprintForge.modules.user.exception;

import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ErrorCode;

public class UsernameAlreadyExistsException extends ConflictException {
    public UsernameAlreadyExistsException(String username) {
        super("Username '" + username + "' is already taken.", ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
