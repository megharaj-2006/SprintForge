package org.SprintForge.modules.workspace.comment.event;

import java.time.LocalDateTime;

public record UserMentionedEvent(
    Long commentId,
    Long mentionId,
    Long mentionedUserId,
    Long actorId,
    LocalDateTime timestamp
) {}
