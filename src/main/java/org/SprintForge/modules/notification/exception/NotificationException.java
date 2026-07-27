package org.SprintForge.modules.notification.exception;

import org.SprintForge.common.exception.BusinessException;




public class NotificationException extends BusinessException {
    public NotificationException(String message) {
        super(message);
    }
}