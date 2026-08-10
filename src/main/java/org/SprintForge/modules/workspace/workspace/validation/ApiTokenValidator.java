package org.SprintForge.modules.workspace.workspace.validation;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceApiToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApiTokenValidator {

    public void validateNotRevoked(WorkspaceApiToken token) {
        if (Boolean.TRUE.equals(token.getIsRevoked())) {
            throw new BusinessRuleException("API Token is revoked.");
        }
    }

    public void validateNotExpired(WorkspaceApiToken token) {
        if (token.getExpiresAt() != null && token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("API Token has expired.");
        }
    }
}
