package org.SprintForge.modules.workspace.comment.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.comment.dto.request.CreateCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.ReplyCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.request.UpdateCommentRequest;
import org.SprintForge.modules.workspace.comment.dto.response.CommentResponse;
import org.SprintForge.modules.workspace.comment.entity.TaskComment;
import org.SprintForge.modules.workspace.comment.event.CommentCreatedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentDeletedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentRepliedEvent;
import org.SprintForge.modules.workspace.comment.event.CommentUpdatedEvent;
import org.SprintForge.modules.workspace.comment.mapper.TaskCommentMapper;
import org.SprintForge.modules.workspace.comment.repository.TaskCommentRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private TaskCommentRepository taskCommentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MentionService mentionService;

    @Mock
    private ReactionService reactionService;

    @Mock
    private TaskCommentMapper taskCommentMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private CommentService commentService;

    private Task mockTask;
    private TaskComment mockComment;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(
                taskCommentRepository,
                taskRepository,
                userRepository,
                mentionService,
                reactionService,
                taskCommentMapper,
                eventPublisher
        );

        Project mockProject = new Project();
        mockProject.setId(100L);

        mockTask = new Task();
        mockTask.setId(10L);
        mockTask.setProject(mockProject);
        mockTask.setArchived(false);
        mockTask.setDeleted(false);

        mockComment = new TaskComment();
        mockComment.setId(20L);
        mockComment.setTaskId(10L);
        mockComment.setUserId(2L);
        mockComment.setContent("Root Comment");
        mockComment.setParentCommentId(null);
        mockComment.setDeleted(false);
    }

    @Test
    void createComment_Success() {
        CreateCommentRequest request = new CreateCommentRequest("Hello World");
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(taskCommentRepository.save(any(TaskComment.class))).thenAnswer(inv -> {
            TaskComment tc = inv.getArgument(0);
            tc.setId(20L);
            return tc;
        });

        CommentResponse expectedResponse = new CommentResponse();
        expectedResponse.setId(20L);
        expectedResponse.setContent("Hello World");
        when(taskCommentMapper.toResponse(any(TaskComment.class), any(), any(), any())).thenReturn(expectedResponse);

        CommentResponse response = commentService.createComment(10L, request, 1L);

        assertNotNull(response);
        assertEquals(20L, response.getId());
        assertEquals("Hello World", response.getContent());
        verify(taskCommentRepository).save(any(TaskComment.class));
        verify(mentionService).extractAndProcessMentions(eq(20L), eq("Hello World"), eq(100L), eq(1L));
        verify(eventPublisher).publishEvent(any(CommentCreatedEvent.class));
    }

    @Test
    void createComment_TaskArchived() {
        CreateCommentRequest request = new CreateCommentRequest("Hello World");
        mockTask.setArchived(true);
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        assertThrows(BusinessRuleException.class, () -> commentService.createComment(10L, request, 1L));
        verify(taskCommentRepository, never()).save(any());
    }

    @Test
    void updateComment_Success() {
        UpdateCommentRequest request = new UpdateCommentRequest("Updated Content");
        when(taskCommentRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockComment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(taskCommentRepository.save(any(TaskComment.class))).thenReturn(mockComment);

        CommentResponse expectedResponse = new CommentResponse();
        expectedResponse.setId(20L);
        expectedResponse.setContent("Updated Content");
        when(taskCommentMapper.toResponse(any(TaskComment.class), any(), any(), any())).thenReturn(expectedResponse);

        CommentResponse response = commentService.updateComment(20L, request, 1L);

        assertNotNull(response);
        assertEquals("Updated Content", response.getContent());
        assertTrue(mockComment.getEdited());
        assertNotNull(mockComment.getEditedAt());
        verify(eventPublisher).publishEvent(any(CommentUpdatedEvent.class));
    }

    @Test
    void updateComment_DeletedComment() {
        UpdateCommentRequest request = new UpdateCommentRequest("Updated Content");
        mockComment.setDeleted(true);
        when(taskCommentRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockComment));

        assertThrows(BusinessRuleException.class, () -> commentService.updateComment(20L, request, 1L));
    }

    @Test
    void reply_Success() {
        ReplyCommentRequest request = new ReplyCommentRequest("This is a reply");
        when(taskCommentRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockComment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(taskCommentRepository.save(any(TaskComment.class))).thenAnswer(inv -> {
            TaskComment tc = inv.getArgument(0);
            tc.setId(21L);
            return tc;
        });

        CommentResponse expectedResponse = new CommentResponse();
        expectedResponse.setId(21L);
        expectedResponse.setContent("This is a reply");
        when(taskCommentMapper.toResponse(any(TaskComment.class), any(), any(), any())).thenReturn(expectedResponse);

        CommentResponse response = commentService.reply(20L, request, 1L);

        assertNotNull(response);
        assertEquals(21L, response.getId());
        verify(taskCommentRepository).save(any(TaskComment.class));
        verify(eventPublisher).publishEvent(any(CommentRepliedEvent.class));
    }

    @Test
    void reply_DepthLimitExceeded() {
        TaskComment reply1 = new TaskComment();
        reply1.setId(21L);
        reply1.setTaskId(10L);
        reply1.setParentCommentId(20L);
        reply1.setDeleted(false);

        TaskComment reply2 = new TaskComment();
        reply2.setId(22L);
        reply2.setTaskId(10L);
        reply2.setParentCommentId(21L);
        reply2.setDeleted(false);

        ReplyCommentRequest request = new ReplyCommentRequest("Too deep");

        when(taskCommentRepository.findByIdAndIsDeletedFalse(22L)).thenReturn(Optional.of(reply2));
        when(taskCommentRepository.findByIdAndIsDeletedFalse(21L)).thenReturn(Optional.of(reply1));
        when(taskCommentRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockComment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        assertThrows(BusinessRuleException.class, () -> commentService.reply(22L, request, 1L));
    }

    @Test
    void deleteComment_Success() {
        when(taskCommentRepository.findByIdAndIsDeletedFalse(20L)).thenReturn(Optional.of(mockComment));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(taskCommentRepository.findByParentCommentIdAndIsDeletedFalseOrderByCreatedAtAsc(20L)).thenReturn(new ArrayList<>());

        commentService.deleteComment(20L, 1L);

        assertTrue(mockComment.isDeleted());
        verify(taskCommentRepository).save(mockComment);
        verify(eventPublisher).publishEvent(any(CommentDeletedEvent.class));
    }
}
