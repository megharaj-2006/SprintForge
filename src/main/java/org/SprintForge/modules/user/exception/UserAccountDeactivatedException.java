package org.SprintForge.modules.user.exception;

import org.SprintForge.common.exception.ErrorCode;
import org.SprintForge.common.exception.ForbiddenException;

public class UserAccountDeactivatedException extends ForbiddenException {
    public UserAccountDeactivatedException(String message) {
        super(message, ErrorCode.PERMISSION_DENIED);
    }
}
