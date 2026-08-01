package org.SprintForge.modules.workspace.comment.event;

import java.time.LocalDateTime;

public record CommentCreatedEvent(
    Long commentId,
    Long actorId,
    LocalDateTime timestamp
) {}
