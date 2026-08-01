package org.SprintForge.modules.workspace.comment.event;

import java.time.LocalDateTime;

public record CommentRepliedEvent(
    Long replyCommentId,
    Long parentCommentId,
    Long actorId,
    LocalDateTime timestamp
) {}
