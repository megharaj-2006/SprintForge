package org.SprintForge.modules.workspace.sprint.exception;

import org.SprintForge.common.exception.BusinessException;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ErrorCode;


public class SprintException extends BusinessRuleException {

    public SprintException(String message) {
        super(message);
    }

    public SprintException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}