package org.SprintForge.modules.workspace.attachment.event;

import java.time.LocalDateTime;

public record AttachmentRestoredEvent(
    Long attachmentId,
    Long actorId,
    LocalDateTime timestamp
) {}
