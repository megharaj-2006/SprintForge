package org.SprintForge.modules.workspace.kanban.exception;

import org.SprintForge.common.exception.BusinessException;




public class KanbanException extends BusinessException {
    public KanbanException(String message) {
        super(message);
    }
}