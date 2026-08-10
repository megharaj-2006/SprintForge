package org.SprintForge.modules.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowCreatedEvent {
    private final Long workflowId;
    private final Long workspaceId;
    private final String name;
    private final String createdBy;
}
