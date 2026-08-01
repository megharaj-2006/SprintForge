package org.SprintForge.modules.workspace.comment.service;

import org.SprintForge.modules.workspace.comment.entity.Mention;
import java.util.List;

public interface MentionService {
    void extractAndProcessMentions(Long commentId, String content, Long projectId, Long actorId);
    List<Mention> getMentionsForComment(Long commentId);
}
