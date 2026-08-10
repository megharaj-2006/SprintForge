package org.SprintForge.modules.workspace.workspace.validation;

import org.SprintForge.common.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebhookValidator {
    private static final List<String> SUPPORTED_EVENTS = List.of(
        "TASK_CREATED", "TASK_UPDATED", "PROJECT_CREATED",
        "MEMBER_JOINED", "INVITATION_ACCEPTED", "RELEASE_PUBLISHED"
    );
    
    public void validateUrl(String url) {
        if (url == null || (!url.startsWith("http://") && !url.startsWith("https://"))) {
            throw new BusinessRuleException("Invalid webhook URL: must start with http:// or https://");
        }
    }
    
    public void validateEvents(List<String> events) {
        if (events == null || events.isEmpty()) {
            throw new BusinessRuleException("At least one event must be specified for a webhook.");
        }
        for (String event : events) {
            if (!SUPPORTED_EVENTS.contains(event)) {
                throw new BusinessRuleException("Unsupported webhook event: " + event);
            }
        }
    }
}
