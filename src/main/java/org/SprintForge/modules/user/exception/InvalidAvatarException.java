package org.SprintForge.modules.user.exception;

import org.SprintForge.common.exception.BadRequestException;
import org.SprintForge.common.exception.ErrorCode;

public class InvalidAvatarException extends BadRequestException {
    public InvalidAvatarException(String message) {
        super(message, ErrorCode.INVALID_INPUT);
    }
}
