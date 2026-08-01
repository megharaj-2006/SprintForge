package org.SprintForge.modules.workspace.comment.repository;

import org.SprintForge.modules.workspace.comment.entity.Mention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentionRepository extends JpaRepository<Mention, Long>, JpaSpecificationExecutor<Mention> {
    List<Mention> findByCommentIdAndIsDeletedFalse(Long commentId);
    List<Mention> findByMentionedUserIdAndIsDeletedFalse(Long mentionedUserId);
}