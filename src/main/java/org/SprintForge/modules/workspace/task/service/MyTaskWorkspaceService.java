package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.MyTasksSummaryResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyTaskWorkspaceService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public MyTasksSummaryResponse getMyTasksSummary(Long userId) {
        List<Task> allTasks = taskRepository.findAll().stream()
                .filter(t -> !t.isDeleted() && t.getStatus() != TaskStatus.DONE)
                .collect(Collectors.toList());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = now.withHour(23).withMinute(59).withSecond(59);

        List<Task> assigned = allTasks.stream()
                .filter(t -> t.getAssignments() != null && t.getAssignments().stream().anyMatch(a -> a.getProjectMember() != null && userId.equals(a.getProjectMember().getWorkspaceMemberId())))
                .collect(Collectors.toList());

        List<Task> dueToday = assigned.stream()
                .filter(t -> t.getDueDate() != null && !t.getDueDate().isBefore(startOfDay) && !t.getDueDate().isAfter(endOfDay))
                .collect(Collectors.toList());

        List<Task> overdue = assigned.stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().isBefore(now))
                .collect(Collectors.toList());

        List<TaskResponse> assignedResponses = assigned.stream().map(taskMapper::toResponse).collect(Collectors.toList());
        List<TaskResponse> dueTodayResponses = dueToday.stream().map(taskMapper::toResponse).collect(Collectors.toList());
        List<TaskResponse> overdueResponses = overdue.stream().map(taskMapper::toResponse).collect(Collectors.toList());

        return MyTasksSummaryResponse.builder()
                .userId(userId)
                .assignedCount(assigned.size())
                .dueTodayCount(dueToday.size())
                .overdueCount(overdue.size())
                .blockedCount(0)
                .assignedTasks(assignedResponses)
                .dueTodayTasks(dueTodayResponses)
                .overdueTasks(overdueResponses)
                .build();
    }
}
