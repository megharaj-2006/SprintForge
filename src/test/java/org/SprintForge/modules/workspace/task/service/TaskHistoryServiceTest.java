package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistoryResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistorySummaryResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskHistory;
import org.SprintForge.modules.workspace.task.entity.enums.TaskHistoryActionType;
import org.SprintForge.modules.workspace.task.mapper.TaskHistoryMapper;
import org.SprintForge.modules.workspace.task.repository.TaskHistoryRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskHistoryServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskHistoryRepository taskHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectPermissionService projectPermissionService;

    @Mock
    private TaskHistoryMapper taskHistoryMapper;

    @InjectMocks
    private TaskHistoryServiceImpl taskHistoryService;

    private Task task;
    private Project project;
    private User user;
    private TaskHistory history;
    private TaskHistoryResponse responseDto;
    private TaskHistorySummaryResponse summaryResponseDto;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(10L);

        task = new Task();
        task.setId(500L);
        task.setProject(project);

        user = new User();
        user.setId(1L);
        user.setUsername("john");

        history = new TaskHistory();
        history.setId(99L);
        history.setTask(task);
        history.setPerformedBy(user);
        history.setActionType(TaskHistoryActionType.STATUS_CHANGED);
        history.setFieldName("status");
        history.setOldValue("TODO");
        history.setNewValue("IN_PROGRESS");
        history.setDescription("Status changed from TODO to IN_PROGRESS.");

        responseDto = TaskHistoryResponse.builder()
                .id(99L)
                .taskId(500L)
                .performedById(1L)
                .performedByUsername("john")
                .actionType(TaskHistoryActionType.STATUS_CHANGED)
                .description("Status changed from TODO to IN_PROGRESS.")
                .build();

        summaryResponseDto = TaskHistorySummaryResponse.builder()
                .id(99L)
                .actionType(TaskHistoryActionType.STATUS_CHANGED)
                .description("Status changed from TODO to IN_PROGRESS.")
                .performedByName("john")
                .build();
    }

    @Test
    void recordHistory_Success() {
        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskHistoryRepository.save(any(TaskHistory.class))).thenReturn(history);

        taskHistoryService.recordHistory(500L, 1L, TaskHistoryActionType.STATUS_CHANGED, "status", "TODO", "IN_PROGRESS", "Status changed.");

        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }

    @Test
    void getTaskHistory_Success() {
        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(projectPermissionService.canViewProject(10L, 1L)).thenReturn(true);
        when(taskHistoryRepository.findByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(500L)).thenReturn(List.of(history));
        when(taskHistoryMapper.toResponseList(anyList())).thenReturn(List.of(responseDto));

        List<TaskHistoryResponse> result = taskHistoryService.getTaskHistory(500L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(99L, result.get(0).getId());
    }

    @Test
    void getTaskHistory_Forbidden() {
        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(projectPermissionService.canViewProject(10L, 1L)).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> taskHistoryService.getTaskHistory(500L, 1L));
    }

    @Test
    void deleteHistory_Success() {
        when(taskRepository.findById(500L)).thenReturn(Optional.of(task));
        when(projectPermissionService.canManageTasks(10L, 1L)).thenReturn(true);

        taskHistoryService.deleteHistory(500L, 1L);

        verify(taskHistoryRepository, times(1)).deleteByTaskId(500L, "1");
    }
}
