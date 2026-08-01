package org.SprintForge.modules.workspace.comment.event;

import java.time.LocalDateTime;

public record CommentReactionAddedEvent(
    Long commentId,
    Long reactionId,
    Long actorId,
    String emoji,
    LocalDateTime timestamp
) {}
