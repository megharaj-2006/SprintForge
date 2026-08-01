package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BadRequestException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.ScheduleTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskScheduleResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskSchedulingService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskScheduleResponse scheduleTask(Long taskId, ScheduleTaskRequest request, Long actorId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        if (request.getStartDate() != null && request.getDueDate() != null && request.getDueDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("Due date cannot be before start date");
        }

        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TaskScheduleResponse> getOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> tasks = taskRepository.findAll().stream()
                .filter(t -> !t.isDeleted() && t.getStatus() != TaskStatus.DONE && t.getDueDate() != null && t.getDueDate().isBefore(now))
                .collect(Collectors.toList());

        return tasks.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskScheduleResponse> getUpcomingTasks() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusDays(7);
        List<Task> tasks = taskRepository.findAll().stream()
                .filter(t -> !t.isDeleted() && t.getStatus() != TaskStatus.DONE && t.getDueDate() != null && t.getDueDate().isAfter(now) && t.getDueDate().isBefore(nextWeek))
                .collect(Collectors.toList());

        return tasks.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskScheduleResponse> getTasksDueToday() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        List<Task> tasks = taskRepository.findAll().stream()
                .filter(t -> !t.isDeleted() && t.getStatus() != TaskStatus.DONE && t.getDueDate() != null && !t.getDueDate().isBefore(startOfDay) && !t.getDueDate().isAfter(endOfDay))
                .collect(Collectors.toList());

        return tasks.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private TaskScheduleResponse mapToResponse(Task t) {
        LocalDateTime now = LocalDateTime.now();
        boolean isOverdue = t.getStatus() != TaskStatus.DONE && t.getDueDate() != null && t.getDueDate().isBefore(now);
        boolean isDueToday = t.getDueDate() != null && t.getDueDate().toLocalDate().equals(now.toLocalDate());

        return TaskScheduleResponse.builder()
                .taskId(t.getId())
                .title(t.getTitle())
                .dueDate(t.getDueDate())
                .isOverdue(isOverdue)
                .isDueToday(isDueToday)
                .build();
    }
}
