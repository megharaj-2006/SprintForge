package org.SprintForge.modules.user.exception;

import org.SprintForge.common.exception.ErrorCode;
import org.SprintForge.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(Long id) {
        super("User not found with ID: " + id, ErrorCode.USER_NOT_FOUND);
    }
}
