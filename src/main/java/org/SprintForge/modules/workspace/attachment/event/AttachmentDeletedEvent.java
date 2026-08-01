package org.SprintForge.modules.workspace.attachment.event;

import java.time.LocalDateTime;

public record AttachmentDeletedEvent(
    Long attachmentId,
    Long actorId,
    LocalDateTime timestamp
) {}
