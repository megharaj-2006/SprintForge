package org.SprintForge.modules.workspace.task.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskTemplateAppliedEvent {
    private final Long templateId;
    private final Long createdTaskId;
    private final Long projectId;
    private final Long actorId;
}
