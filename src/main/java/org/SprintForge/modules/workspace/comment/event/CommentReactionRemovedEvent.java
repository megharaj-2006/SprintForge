package org.SprintForge.modules.workspace.comment.event;

import java.time.LocalDateTime;

public record CommentReactionRemovedEvent(
    Long commentId,
    Long reactionId,
    Long actorId,
    String emoji,
    LocalDateTime timestamp
) {}
