package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record LabelCreatedEvent(
    Long labelId,
    Long projectId,
    String name,
    Long actorId,
    LocalDateTime timestamp
) {}