package org.SprintForge.modules.workspace.task.event;

import java.time.LocalDateTime;

public record LabelArchivedEvent(
    Long labelId,
    Long projectId,
    Long actorId,
    LocalDateTime timestamp
) {}