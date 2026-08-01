package org.SprintForge.modules.workspace.comment.event;

import java.time.LocalDateTime;

public record CommentUpdatedEvent(
    Long commentId,
    Long actorId,
    LocalDateTime timestamp
) {}
