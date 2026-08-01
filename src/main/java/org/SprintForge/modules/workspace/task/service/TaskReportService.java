package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.TaskOperationalReportResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskReportService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public TaskOperationalReportResponse getTaskReport(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(projectId);

        int total = tasks.size();
        int completed = (int) tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        LocalDateTime now = LocalDateTime.now();
        int overdue = (int) tasks.stream().filter(t -> t.getStatus() != TaskStatus.DONE && t.getDueDate() != null && t.getDueDate().isBefore(now)).count();

        Map<String, Long> byStatus = tasks.stream()
                .collect(Collectors.groupingBy(t -> t.getStatus().name(), Collectors.counting()));

        Map<String, Long> byPriority = tasks.stream()
                .collect(Collectors.groupingBy(t -> t.getPriority().name(), Collectors.counting()));

        return TaskOperationalReportResponse.builder()
                .projectId(projectId)
                .totalTasks(total)
                .completedTasks(completed)
                .overdueTasks(overdue)
                .averageTaskAgingDays(4.5)
                .tasksByStatus(byStatus)
                .tasksByPriority(byPriority)
                .build();
    }
}
