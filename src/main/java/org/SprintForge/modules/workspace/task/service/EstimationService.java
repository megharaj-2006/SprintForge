package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.EstimateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskEstimateResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskEstimate;
import org.SprintForge.modules.workspace.task.repository.TaskEstimateRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstimationService {

    private final TaskEstimateRepository estimateRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public TaskEstimateResponse estimateTask(Long taskId, EstimateTaskRequest request, Long actorId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        Double actual = request.getActualValue() != null ? request.getActualValue() : task.getActualHours();
        Double variance = actual != null ? actual - request.getEstimatedValue() : null;

        TaskEstimate estimate = new TaskEstimate();
        estimate.setTaskId(taskId);
        estimate.setEstimateType(request.getEstimateType());
        estimate.setEstimatedValue(request.getEstimatedValue());
        estimate.setActualValue(actual);
        estimate.setVariance(variance);
        estimate.setEstimatedBy(actorId);

        if (request.getEstimateType().equalsIgnoreCase("STORY_POINTS")) {
            task.setStoryPoints(request.getEstimatedValue().intValue());
        } else {
            task.setEstimatedHours(request.getEstimatedValue());
        }
        taskRepository.save(task);

        TaskEstimate saved = estimateRepository.save(estimate);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TaskEstimateResponse> getEstimateHistory(Long taskId) {
        List<TaskEstimate> history = estimateRepository.findByTaskIdOrderByCreatedAtDesc(taskId);
        return history.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private TaskEstimateResponse mapToResponse(TaskEstimate e) {
        return TaskEstimateResponse.builder()
                .id(e.getId())
                .taskId(e.getTaskId())
                .estimateType(e.getEstimateType())
                .estimatedValue(e.getEstimatedValue())
                .actualValue(e.getActualValue())
                .variance(e.getVariance())
                .estimatedBy(e.getEstimatedBy())
                .createdAt(e.getCreatedAt())
                .build();
    }
}
