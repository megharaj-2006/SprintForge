package org.SprintForge.modules.workspace.sprint.exception;

import org.SprintForge.common.exception.ErrorCode;
import org.SprintForge.common.exception.ResourceNotFoundException;

public class SprintNotFoundException extends ResourceNotFoundException {

    public SprintNotFoundException(Long sprintId) {
        super(String.format("Sprint not found with ID: '%s'", sprintId), ErrorCode.TASK_NOT_FOUND);
    }

    public SprintNotFoundException(String message) {
        super(message, ErrorCode.TASK_NOT_FOUND);
    }
}
