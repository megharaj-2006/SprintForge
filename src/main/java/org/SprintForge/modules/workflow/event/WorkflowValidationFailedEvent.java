package org.SprintForge.modules.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkflowValidationFailedEvent {
    private final Long workflowId;
    private final Long versionId;
    private final List<String> errorMessages;
}
