package org.SprintForge.modules.workspace.attachment.event;

import java.time.LocalDateTime;

public record AttachmentUploadedEvent(
    Long attachmentId,
    Long actorId,
    LocalDateTime timestamp
) {}
