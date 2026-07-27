package org.SprintForge.modules.workspace.task.exception;

import org.SprintForge.common.exception.BusinessException;




public class TaskException extends BusinessException {
    public TaskException(String message) {
        super(message);
    }
}