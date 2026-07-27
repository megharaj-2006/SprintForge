package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record LabelRemovedEvent(
    Long taskId,
    Long labelId,
    Long actorId,
    LocalDateTime timestamp
) {}