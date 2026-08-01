package org.SprintForge.modules.workspace.task.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskTemplateCreatedEvent {
    private final Long templateId;
    private final Long workspaceId;
    private final Long actorId;
}
