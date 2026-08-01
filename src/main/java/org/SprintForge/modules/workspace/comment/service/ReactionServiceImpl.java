package org.SprintForge.modules.workspace.comment.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.comment.dto.response.ReactionResponse;
import org.SprintForge.modules.workspace.comment.entity.CommentReaction;
import org.SprintForge.modules.workspace.comment.entity.TaskComment;
import org.SprintForge.modules.workspace.comment.event.CommentReactionAddedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentReactionRemovedEvent;
import org.SprintForge.modules.workspace.comment.mapper.TaskCommentMapper;
import org.SprintForge.modules.workspace.comment.repository.CommentReactionRepository;
import org.SprintForge.modules.workspace.comment.repository.TaskCommentRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final CommentReactionRepository commentReactionRepository;
    private final UserRepository userRepository;
    private final TaskCommentMapper taskCommentMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ReactionResponse addReaction(Long commentId, String emoji, Long actorId) {
        TaskComment comment = getCommentOrThrow(commentId);
        Task task = getTaskOrThrow(comment.getTaskId());
        validateTaskNotArchived(task);

        if (commentReactionRepository.existsByCommentIdAndUserIdAndEmojiAndIsDeletedFalse(commentId, actorId, emoji)) {
            throw new BusinessRuleException("User has already reacted with this emoji to this comment.");
        }

        CommentReaction reaction = new CommentReaction();
        reaction.setCommentId(commentId);
        reaction.setUserId(actorId);
        reaction.setEmoji(emoji);
        reaction.setCreatedBy(actorId.toString());

        CommentReaction saved = commentReactionRepository.save(reaction);
        eventPublisher.publishEvent(new CommentReactionAddedEvent(
                commentId,
                saved.getId(),
                actorId,
                emoji,
                LocalDateTime.now()
        ));

        String username = getUserName(actorId);
        return taskCommentMapper.toResponse(saved, username);
    }

    @Override
    @Transactional
    public void removeReaction(Long commentId, String emoji, Long actorId) {
        TaskComment comment = getCommentOrThrow(commentId);
        Task task = getTaskOrThrow(comment.getTaskId());
        validateTaskNotArchived(task);

        CommentReaction reaction = commentReactionRepository.findByCommentIdAndUserIdAndEmojiAndIsDeletedFalse(commentId, actorId, emoji)
                .orElseThrow(() -> new ResourceNotFoundException("Reaction not found."));

        reaction.markDeleted(actorId.toString());
        commentReactionRepository.save(reaction);

        eventPublisher.publishEvent(new CommentReactionRemovedEvent(
                commentId,
                reaction.getId(),
                actorId,
                emoji,
                LocalDateTime.now()
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReactionResponse> getReactionsForComment(Long commentId) {
        List<CommentReaction> reactions = commentReactionRepository.findByCommentIdAndIsDeletedFalse(commentId);
        List<ReactionResponse> responses = new ArrayList<>();
        for (CommentReaction r : reactions) {
            String username = getUserName(r.getUserId());
            responses.add(taskCommentMapper.toResponse(r, username));
        }
        return responses;
    }

    private TaskComment getCommentOrThrow(Long commentId) {
        return taskCommentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
    }

    private void validateTaskNotArchived(Task task) {
        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }
    }

    private String getUserName(Long userId) {
        return userRepository.findById(userId)
                .map(User::getUsername)
                .orElse("Unknown");
    }
}
