package org.SprintForge.modules.workspace.customfield.event;

import java.time.LocalDateTime;

public record CustomFieldArchivedEvent(
    Long customFieldId,
    Long projectId,
    Long actorId,
    LocalDateTime timestamp
) {}
