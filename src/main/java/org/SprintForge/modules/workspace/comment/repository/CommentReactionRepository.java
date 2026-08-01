package org.SprintForge.modules.workspace.comment.repository;

import org.SprintForge.modules.workspace.comment.entity.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long>, JpaSpecificationExecutor<CommentReaction> {
    List<CommentReaction> findByCommentIdAndIsDeletedFalse(Long commentId);
    Optional<CommentReaction> findByCommentIdAndUserIdAndEmojiAndIsDeletedFalse(Long commentId, Long userId, String emoji);
    boolean existsByCommentIdAndUserIdAndEmojiAndIsDeletedFalse(Long commentId, Long userId, String emoji);
}