package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.response.TaskHealthResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskHealthService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public TaskHealthResponse calculateTaskHealth(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        List<String> warnings = new ArrayList<>();
        int score = 100;

        boolean missingAssignee = task.getAssignments() == null || task.getAssignments().isEmpty();
        if (missingAssignee) {
            score -= 15;
            warnings.add("Task has no assigned team member");
        }

        boolean missingEstimate = task.getEstimatedHours() == null || task.getEstimatedHours() == 0;
        if (missingEstimate) {
            score -= 15;
            warnings.add("Task is missing time estimation");
        }

        boolean missingDueDate = task.getDueDate() == null;
        if (missingDueDate) {
            score -= 15;
            warnings.add("Task has no due date specified");
        }

        LocalDateTime now = LocalDateTime.now();
        boolean isOverdue = task.getStatus() != TaskStatus.DONE && task.getDueDate() != null && task.getDueDate().isBefore(now);
        if (isOverdue) {
            score -= 35;
            warnings.add("Task is past its due date");
        }

        boolean isStale = task.getUpdatedAt() != null && task.getUpdatedAt().isBefore(now.minusDays(14));
        if (isStale) {
            score -= 20;
            warnings.add("Task has not been updated in over 14 days");
        }

        score = Math.max(0, score);
        String status = score >= 80 ? "HEALTHY" : (score >= 50 ? "WARNING" : "CRITICAL");

        return TaskHealthResponse.builder()
                .taskId(taskId)
                .healthScore(score)
                .healthStatus(status)
                .isOverdue(isOverdue)
                .isBlocked(false)
                .isStale(isStale)
                .missingAssignee(missingAssignee)
                .missingEstimate(missingEstimate)
                .missingDueDate(missingDueDate)
                .healthWarnings(warnings)
                .build();
    }

    @Transactional(readOnly = true)
    public List<TaskHealthResponse> getStaleTasks() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(14);
        List<Task> tasks = taskRepository.findAll().stream()
                .filter(t -> !t.isDeleted() && t.getStatus() != TaskStatus.DONE && t.getUpdatedAt() != null && t.getUpdatedAt().isBefore(cutoff))
                .collect(Collectors.toList());

        return tasks.stream().map(t -> calculateTaskHealth(t.getId())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskHealthResponse> getRiskTasks() {
        return taskRepository.findAll().stream()
                .filter(t -> !t.isDeleted() && t.getStatus() != TaskStatus.DONE)
                .map(t -> calculateTaskHealth(t.getId()))
                .filter(h -> "CRITICAL".equals(h.getHealthStatus()))
                .collect(Collectors.toList());
    }
}
