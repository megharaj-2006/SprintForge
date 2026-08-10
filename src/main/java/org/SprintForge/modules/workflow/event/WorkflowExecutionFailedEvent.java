package org.SprintForge.modules.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowExecutionFailedEvent {
    private final Long executionId;
    private final Long workflowVersionId;
    private final String errorMessage;
}
