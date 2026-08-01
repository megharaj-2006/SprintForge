package org.SprintForge.modules.workspace.task.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskTemplateUpdatedEvent {
    private final Long templateId;
    private final Long actorId;
}
