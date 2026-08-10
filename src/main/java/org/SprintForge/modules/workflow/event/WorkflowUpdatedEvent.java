package org.SprintForge.modules.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowUpdatedEvent {
    private final Long workflowId;
    private final String name;
    private final String updatedBy;
}
