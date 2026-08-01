package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.InvalidOperationException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.ApplyTaskTemplateRequest;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.*;
import org.SprintForge.modules.workspace.task.event.TaskTemplateAppliedEvent;
import org.SprintForge.modules.workspace.task.repository.TaskTemplateRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskTemplateApplicationService {

    private final TaskTemplateRepository templateRepository;
    private final TaskService taskService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public TaskResponse applyTemplate(Long templateId, ApplyTaskTemplateRequest request, Long actorId) {
        log.info("Applying task template {} to project {} by user {}", templateId, request.getProjectId(), actorId);

        TaskTemplate template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Task template not found with ID: " + templateId));

        if (template.isDeleted()) {
            throw new ResourceNotFoundException("Task template not found with ID: " + templateId);
        }

        if (Boolean.TRUE.equals(template.getIsArchived())) {
            throw new InvalidOperationException("Archived template cannot be applied to create tasks");
        }

        String taskTitle = (request.getTaskTitleOverride() != null && !request.getTaskTitleOverride().isBlank())
                ? request.getTaskTitleOverride()
                : template.getName();

        CreateTaskRequest createReq = CreateTaskRequest.builder()
                .projectId(request.getProjectId())
                .title(taskTitle)
                .description(template.getDescription())
                .estimatedHours(template.getEstimatedHours())
                .storyPoints(template.getStoryPoints())
                .dueDate(request.getDueDate() != null ? request.getDueDate().atStartOfDay() : null)
                .sprintId(request.getSprintId())
                .build();

        TaskResponse createdTask = taskService.createTask(createReq, actorId);

        templateRepository.incrementUsageCount(templateId);

        eventPublisher.publishEvent(new TaskTemplateAppliedEvent(
                templateId,
                createdTask.getId(),
                request.getProjectId(),
                actorId
        ));

        return createdTask;
    }
}
