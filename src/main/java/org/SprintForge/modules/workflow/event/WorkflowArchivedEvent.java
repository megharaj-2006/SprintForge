package org.SprintForge.modules.workflow.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkflowArchivedEvent {
    private final Long workflowId;
    private final String archivedBy;
}
