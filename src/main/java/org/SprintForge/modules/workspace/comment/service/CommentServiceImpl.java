package org.SprintForge.modules.workspace.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.comment.dto.request.CreateCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.ReplyCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.UpdateCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.response.CommentResponse;
import org.SprintForge.modules.workspace.comment.dto.response.ReactionResponse;
import org.SprintForge.modules.workspace.comment.entity.TaskComment;
import org.SprintForge.modules.workspace.comment.event.CommentCreatedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentDeletedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentRepliedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentUpdatedEvent;
import org.SprintForge.modules.workspace.comment.mapper.TaskCommentMapper;
import org.SprintForge.modules.workspace.comment.repository.TaskCommentRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MentionService mentionService;
    private final ReactionService reactionService;
    private final TaskCommentMapper taskCommentMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CommentResponse createComment(Long taskId, CreateCommentRequest request, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskNotArchived(task);

        TaskComment comment = new TaskComment();
        comment.setTaskId(taskId);
        comment.setUserId(actorId);
        comment.setContent(request.getContent());
        comment.setParentCommentId(null);
        comment.setCreatedBy(actorId.toString());

        TaskComment saved = taskCommentRepository.save(comment);

        // Process Mentions
        mentionService.extractAndProcessMentions(saved.getId(), request.getContent(), task.getProject().getId(), actorId);

        eventPublisher.publishEvent(new CommentCreatedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return mapToResponse(saved, Collections.emptyList());
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long commentId, UpdateCommentRequest request, Long actorId) {
        TaskComment comment = getCommentOrThrow(commentId);
        if (Boolean.TRUE.equals(comment.isDeleted())) {
            throw new BusinessRuleException("Deleted comments cannot be edited.");
        }

        Task task = getTaskOrThrow(comment.getTaskId());
        validateTaskNotArchived(task);

        comment.setContent(request.getContent());
        comment.setEdited(true);
        comment.setEditedAt(LocalDateTime.now());
        comment.setUpdatedBy(actorId.toString());

        TaskComment saved = taskCommentRepository.save(comment);

        // Re-process Mentions
        mentionService.extractAndProcessMentions(saved.getId(), request.getContent(), task.getProject().getId(), actorId);

        eventPublisher.publishEvent(new CommentUpdatedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long actorId) {
        TaskComment comment = getCommentOrThrow(commentId);
        Task task = getTaskOrThrow(comment.getTaskId());
        validateTaskNotArchived(task);

        comment.markDeleted(actorId.toString());
        taskCommentRepository.save(comment);

        // Soft delete nested replies recursively
        List<TaskComment> replies = taskCommentRepository.findByParentCommentIdAndIsDeletedFalseOrderByCreatedAtAsc(commentId);
        for (TaskComment reply : replies) {
            deleteCommentRecursive(reply, actorId.toString());
        }

        eventPublisher.publishEvent(new CommentDeletedEvent(commentId, actorId, LocalDateTime.now()));
    }

    private void deleteCommentRecursive(TaskComment comment, String deletedBy) {
        comment.markDeleted(deletedBy);
        taskCommentRepository.save(comment);

        List<TaskComment> replies = taskCommentRepository.findByParentCommentIdAndIsDeletedFalseOrderByCreatedAtAsc(comment.getId());
        for (TaskComment reply : replies) {
            deleteCommentRecursive(reply, deletedBy);
        }
    }

    @Override
    @Transactional
    public CommentResponse reply(Long commentId, ReplyCommentRequest request, Long actorId) {
        TaskComment parent = getCommentOrThrow(commentId);
        if (Boolean.TRUE.equals(parent.isDeleted())) {
            throw new BusinessRuleException("Cannot reply to a deleted comment.");
        }

        Task task = getTaskOrThrow(parent.getTaskId());
        validateTaskNotArchived(task);

        // Validate Depth (Depth Limit = 2: Root -> Reply -> ReplyToReply)
        int depth = 0;
        TaskComment current = parent;
        while (current.getParentCommentId() != null) {
            depth++;
            current = getCommentOrThrow(current.getParentCommentId());
            if (depth >= 2) {
                throw new BusinessRuleException("Nested replies are limited to a depth of 2.");
            }
        }

        TaskComment reply = new TaskComment();
        reply.setTaskId(parent.getTaskId());
        reply.setUserId(actorId);
        reply.setContent(request.getContent());
        reply.setParentCommentId(commentId);
        reply.setCreatedBy(actorId.toString());

        TaskComment saved = taskCommentRepository.save(reply);

        // Process Mentions
        mentionService.extractAndProcessMentions(saved.getId(), request.getContent(), task.getProject().getId(), actorId);

        eventPublisher.publishEvent(new CommentRepliedEvent(saved.getId(), commentId, actorId, LocalDateTime.now()));

        return mapToResponse(saved, Collections.emptyList());
    }

    @Override
    @Transactional
    public CommentResponse edit(Long commentId, UpdateCommentRequest request, Long actorId) {
        return updateComment(commentId, request, actorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getTaskComments(Long taskId, Long actorId) {
        getTaskOrThrow(taskId);

        // Find root level comments
        List<TaskComment> roots = taskCommentRepository.findByTaskIdAndParentCommentIdIsNullAndIsDeletedFalseOrderByCreatedAtAsc(taskId);
        List<CommentResponse> responses = new ArrayList<>();

        for (TaskComment r : roots) {
            responses.add(buildCommentTree(r));
        }

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getReplies(Long commentId, Long actorId) {
        getCommentOrThrow(commentId);
        List<TaskComment> replies = taskCommentRepository.findByParentCommentIdAndIsDeletedFalseOrderByCreatedAtAsc(commentId);
        List<CommentResponse> responses = new ArrayList<>();

        for (TaskComment r : replies) {
            responses.add(buildCommentTree(r));
        }
        return responses;
    }

    private CommentResponse buildCommentTree(TaskComment comment) {
        List<TaskComment> childEntities = taskCommentRepository.findByParentCommentIdAndIsDeletedFalseOrderByCreatedAtAsc(comment.getId());
        List<CommentResponse> replies = new ArrayList<>();
        for (TaskComment child : childEntities) {
            replies.add(buildCommentTree(child));
        }
        return mapToResponse(comment, replies);
    }

    private CommentResponse mapToResponse(TaskComment comment, List<CommentResponse> replies) {
        String username = getUserName(comment.getUserId());
        List<ReactionResponse> reactions = reactionService.getReactionsForComment(comment.getId());
        return taskCommentMapper.toResponse(comment, username, replies, reactions);
    }

    private CommentResponse mapToResponse(TaskComment comment) {
        List<TaskComment> childEntities = taskCommentRepository.findByParentCommentIdAndIsDeletedFalseOrderByCreatedAtAsc(comment.getId());
        List<CommentResponse> replies = new ArrayList<>();
        for (TaskComment child : childEntities) {
            replies.add(buildCommentTree(child));
        }
        return mapToResponse(comment, replies);
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