package org.SprintForge.modules.workspace.attachment.event;

import java.time.LocalDateTime;

public record AttachmentRenamedEvent(
    Long attachmentId,
    String oldName,
    String newName,
    Long actorId,
    LocalDateTime timestamp
) {}
