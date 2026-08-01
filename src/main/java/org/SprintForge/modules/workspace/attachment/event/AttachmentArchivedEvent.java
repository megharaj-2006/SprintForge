package org.SprintForge.modules.workspace.attachment.event;

import java.time.LocalDateTime;

public record AttachmentArchivedEvent(
    Long attachmentId,
    Long actorId,
    LocalDateTime timestamp
) {}
