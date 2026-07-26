package org.SprintForge.modules.activity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActivityException extends RuntimeException {
    public ActivityException(String message) {
        super(message);
    }
}