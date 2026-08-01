package org.SprintForge.modules.workspace.comment.service;

import org.SprintForge.modules.workspace.comment.dto.response.ReactionResponse;
import java.util.List;

public interface ReactionService {
    ReactionResponse addReaction(Long commentId, String emoji, Long actorId);
    void removeReaction(Long commentId, String emoji, Long actorId);
    List<ReactionResponse> getReactionsForComment(Long commentId);
}
