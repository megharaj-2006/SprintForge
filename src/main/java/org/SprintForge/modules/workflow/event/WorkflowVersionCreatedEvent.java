package org.SprintForge.modules.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowVersionCreatedEvent {
    private final Long workflowId;
    private final Long versionId;
    private final Integer versionNumber;
    private final String createdBy;
}
