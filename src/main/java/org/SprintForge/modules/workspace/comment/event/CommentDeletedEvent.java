package org.SprintForge.modules.workspace.comment.event;

import java.time.LocalDateTime;

public record CommentDeletedEvent(
    Long commentId,
    Long actorId,
    LocalDateTime timestamp
) {}
