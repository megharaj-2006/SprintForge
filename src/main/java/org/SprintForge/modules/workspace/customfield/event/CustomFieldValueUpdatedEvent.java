package org.SprintForge.modules.workspace.customfield.event;

import java.time.LocalDateTime;

public record CustomFieldValueUpdatedEvent(
    Long valueId,
    Long taskId,
    Long customFieldId,
    Long actorId,
    LocalDateTime timestamp
) {}
