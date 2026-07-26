package org.SprintForge.common.exception;

public class InvalidOperationException extends BadRequestException {

    public InvalidOperationException(String message) {
        super(message);
    }
}
