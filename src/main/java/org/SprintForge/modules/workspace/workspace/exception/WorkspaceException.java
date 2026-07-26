package org.SprintForge.modules.workspace.workspace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WorkspaceException extends RuntimeException {
    public WorkspaceException(String message) {
        super(message);
    }
}