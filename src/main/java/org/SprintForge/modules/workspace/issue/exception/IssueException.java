package org.SprintForge.modules.workspace.issue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IssueException extends RuntimeException {
    public IssueException(String message) {
        super(message);
    }
}