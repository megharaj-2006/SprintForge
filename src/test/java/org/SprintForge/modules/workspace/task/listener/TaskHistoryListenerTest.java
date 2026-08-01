package org.SprintForge.modules.workspace.task.listener;

import org.SprintForge.modules.workspace.task.entity.enums.TaskHistoryActionType;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.event.TaskCreatedEvent;
import org.SprintForge.modules.workspace.task.event.TaskStatusChangedEvent;
import org.SprintForge.modules.workspace.task.service.TaskHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskHistoryListenerTest {

    @Mock
    private TaskHistoryService taskHistoryService;

    @InjectMocks
    private TaskHistoryListener taskHistoryListener;

    @Test
    void handleTaskCreated() {
        TaskCreatedEvent event = new TaskCreatedEvent(500L, 10L, 1L, LocalDateTime.now());

        taskHistoryListener.handleTaskCreated(event);

        verify(taskHistoryService, times(1)).recordHistory(
                eq(500L),
                eq(1L),
                eq(TaskHistoryActionType.TASK_CREATED),
                isNull(),
                isNull(),
                isNull(),
                anyString()
        );
    }

    @Test
    void handleTaskStatusChanged() {
        TaskStatusChangedEvent event = new TaskStatusChangedEvent(
                500L, TaskStatus.TODO, TaskStatus.IN_PROGRESS, 1L, LocalDateTime.now());

        taskHistoryListener.handleTaskStatusChanged(event);

        verify(taskHistoryService, times(1)).recordHistory(
                eq(500L),
                eq(1L),
                eq(TaskHistoryActionType.STATUS_CHANGED),
                eq("status"),
                eq("TODO"),
                eq("IN_PROGRESS"),
                anyString()
        );
    }
}
