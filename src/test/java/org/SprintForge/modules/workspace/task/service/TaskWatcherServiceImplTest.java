package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.task.dto.request.AddWatcherRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskWatcherResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskWatcher;
import org.SprintForge.modules.workspace.task.event.TaskWatcherAddedEvent;
import org.SprintForge.modules.workspace.task.event.TaskWatcherRemovedEvent;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.mapper.TaskWatcherMapper;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.task.repository.TaskWatcherRepository;
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
class TaskWatcherServiceImplTest {

    @Mock
    private TaskWatcherRepository taskWatcherRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskWatcherMapper taskWatcherMapper;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private TaskWatcherService taskWatcherService;

    private Task mockTask;
    private User mockUser;
    private TaskWatcher mockWatcher;

    @BeforeEach
    void setUp() {
        taskWatcherService = new TaskWatcherServiceImpl(
                taskWatcherRepository,
                taskRepository,
                userRepository,
                taskWatcherMapper,
                taskMapper,
                eventPublisher
        );

        mockTask = new Task();
        mockTask.setId(10L);
        mockTask.setArchived(false);
        mockTask.setDeleted(false);

        mockUser = new User();
        mockUser.setId(5L);
        mockUser.setUsername("testuser");

        mockWatcher = new TaskWatcher();
        mockWatcher.setId(50L);
        mockWatcher.setTaskId(10L);
        mockWatcher.setUserId(5L);
        mockWatcher.setWatchingSince(LocalDateTime.now());
        mockWatcher.setNotificationPreference("ALL");
        mockWatcher.setDeleted(false);
    }

    @Test
    void addWatcher_Success() {
        AddWatcherRequest request = new AddWatcherRequest(5L, "ALL");
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(userRepository.findById(5L)).thenReturn(Optional.of(mockUser));
        when(taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(10L, 5L)).thenReturn(Optional.empty());
        when(taskWatcherRepository.save(any(TaskWatcher.class))).thenAnswer(inv -> {
            TaskWatcher tw = inv.getArgument(0);
            tw.setId(50L);
            return tw;
        });

        TaskWatcherResponse expectedResponse = new TaskWatcherResponse();
        expectedResponse.setId(50L);
        expectedResponse.setUsername("testuser");
        when(taskWatcherMapper.toResponse(any(TaskWatcher.class), eq("testuser"))).thenReturn(expectedResponse);

        TaskWatcherResponse response = taskWatcherService.addWatcher(10L, request, 1L);

        assertNotNull(response);
        assertEquals(50L, response.getId());
        assertEquals("testuser", response.getUsername());
        verify(taskWatcherRepository).save(any(TaskWatcher.class));
        verify(eventPublisher).publishEvent(any(TaskWatcherAddedEvent.class));
    }

    @Test
    void addWatcher_TaskArchived() {
        AddWatcherRequest request = new AddWatcherRequest(5L, "ALL");
        mockTask.setArchived(true);
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        assertThrows(BusinessRuleException.class, () -> taskWatcherService.addWatcher(10L, request, 1L));
        verify(taskWatcherRepository, never()).save(any());
    }

    @Test
    void addWatcher_DuplicateWatcher() {
        AddWatcherRequest request = new AddWatcherRequest(5L, "ALL");
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(userRepository.findById(5L)).thenReturn(Optional.of(mockUser));
        when(taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(10L, 5L)).thenReturn(Optional.of(mockWatcher));

        assertThrows(BusinessRuleException.class, () -> taskWatcherService.addWatcher(10L, request, 1L));
    }

    @Test
    void removeWatcher_Success() {
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(10L, 5L)).thenReturn(Optional.of(mockWatcher));

        taskWatcherService.removeWatcher(10L, 5L, 1L);

        assertTrue(mockWatcher.isDeleted());
        verify(taskWatcherRepository).save(mockWatcher);
        verify(eventPublisher).publishEvent(any(TaskWatcherRemovedEvent.class));
    }

    @Test
    void toggleWatcher_RemoveFlow() {
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(10L, 1L)).thenReturn(Optional.of(mockWatcher));

        TaskWatcherResponse response = taskWatcherService.toggleWatcher(10L, 1L);

        assertNull(response);
        assertTrue(mockWatcher.isDeleted());
        verify(eventPublisher).publishEvent(any(TaskWatcherRemovedEvent.class));
    }

    @Test
    void toggleWatcher_AddFlow() {
        User actor = new User();
        actor.setId(1L);
        actor.setUsername("actorUser");

        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(10L, 1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(actor));
        when(taskWatcherRepository.save(any(TaskWatcher.class))).thenAnswer(inv -> {
            TaskWatcher tw = inv.getArgument(0);
            tw.setId(99L);
            return tw;
        });

        TaskWatcherResponse expectedResponse = new TaskWatcherResponse();
        expectedResponse.setId(99L);
        expectedResponse.setUsername("actorUser");
        when(taskWatcherMapper.toResponse(any(TaskWatcher.class), eq("actorUser"))).thenReturn(expectedResponse);

        TaskWatcherResponse response = taskWatcherService.toggleWatcher(10L, 1L);

        assertNotNull(response);
        assertEquals(99L, response.getId());
        assertEquals("actorUser", response.getUsername());
        verify(eventPublisher).publishEvent(any(TaskWatcherAddedEvent.class));
    }
}
