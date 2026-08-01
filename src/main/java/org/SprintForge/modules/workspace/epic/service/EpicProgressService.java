package org.SprintForge.modules.workspace.epic.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.epic.dto.response.EpicProgressResponse;
import org.SprintForge.modules.workspace.epic.entity.Epic;
import org.SprintForge.modules.workspace.epic.repository.EpicRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpicProgressService {

    private final EpicRepository epicRepository;
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public EpicProgressResponse calculateProgress(Long epicId) {
        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new ResourceNotFoundException("Epic not found with ID: " + epicId));

        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(epic.getProjectId()); // Filter by epic if task has epic relation or parent

        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        double pct = totalTasks > 0 ? ((double) completedTasks / totalTasks) * 100.0 : 0.0;

        int totalSp = tasks.stream().mapToInt(t -> t.getStoryPoints() != null ? t.getStoryPoints() : 0).sum();
        int completedSp = tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).mapToInt(t -> t.getStoryPoints() != null ? t.getStoryPoints() : 0).sum();

        double estimatedHours = tasks.stream().mapToDouble(t -> t.getEstimatedHours() != null ? t.getEstimatedHours() : 0.0).sum();
        double actualHours = tasks.stream().mapToDouble(t -> t.getActualHours() != null ? t.getActualHours() : 0.0).sum();
        double remainingHours = Math.max(0.0, estimatedHours - actualHours);

        return EpicProgressResponse.builder()
                .epicId(epicId)
                .epicName(epic.getName())
                .totalTaskCount(totalTasks)
                .completedTaskCount(completedTasks)
                .completedPercentage(pct)
                .totalStoryPoints(totalSp)
                .completedStoryPoints(completedSp)
                .estimatedHours(estimatedHours)
                .remainingHours(remainingHours)
                .build();
    }
}
