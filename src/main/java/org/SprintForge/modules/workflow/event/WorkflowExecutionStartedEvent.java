package org.SprintForge.modules.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowExecutionStartedEvent {
    private final Long executionId;
    private final Long workflowVersionId;
    private final String triggeredBy;
}
