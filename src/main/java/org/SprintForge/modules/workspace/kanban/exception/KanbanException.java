package org.SprintForge.modules.workspace.kanban.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class KanbanException extends RuntimeException {
    public KanbanException(String message) {
        super(message);
    }
}