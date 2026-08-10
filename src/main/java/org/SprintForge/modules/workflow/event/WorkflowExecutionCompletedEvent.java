package org.SprintForge.modules.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowExecutionCompletedEvent {
    private final Long executionId;
    private final Long workflowVersionId;
    private final Long durationMillis;
    private final String result;
}
