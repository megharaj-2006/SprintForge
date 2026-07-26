package org.SprintForge.modules.workspace.sprint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SprintException extends RuntimeException {
    public SprintException(String message) {
        super(message);
    }
}