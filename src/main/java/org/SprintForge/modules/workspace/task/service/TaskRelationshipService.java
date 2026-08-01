package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.DuplicateResourceException;
import org.SprintForge.common.exception.InvalidOperationException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRelationshipRequest;
import org.SprintForge.modules.workspace.task.entity.AdvancedTaskRelationship;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.AdvancedTaskRelationshipRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskRelationshipService {

    private final AdvancedTaskRelationshipRepository relationshipRepository;
    private final TaskRepository taskRepository;
    private final DependencyGraphService graphService;

    @Transactional
    public AdvancedTaskRelationship createRelationship(Long sourceTaskId, CreateTaskRelationshipRequest request, Long actorId) {
        log.info("Creating relationship {} between task {} and task {} by user {}", request.getRelationshipType(), sourceTaskId, request.getTargetTaskId(), actorId);

        Task source = findTaskOrThrow(sourceTaskId);
        Task target = findTaskOrThrow(request.getTargetTaskId());

        if (!source.getProject().getId().equals(target.getProject().getId())) {
            throw new BusinessRuleException("Relationships can only be created between tasks in the same project");
        }
        if ((source.getArchived() != null && source.getArchived()) || (target.getArchived() != null && target.getArchived())) {
            throw new InvalidOperationException("Cannot create relationship with an archived task");
        }

        if (relationshipRepository.existsBySourceTaskIdAndTargetTaskIdAndRelationshipTypeAndIsDeletedFalse(sourceTaskId, request.getTargetTaskId(), request.getRelationshipType())) {
            throw new DuplicateResourceException("Relationship already exists");
        }

        if ("BLOCKS".equalsIgnoreCase(request.getRelationshipType())) {
            graphService.checkForCircularDependency(sourceTaskId, request.getTargetTaskId());
        }

        AdvancedTaskRelationship rel = new AdvancedTaskRelationship();
        rel.setSourceTaskId(sourceTaskId);
        rel.setTargetTaskId(request.getTargetTaskId());
        rel.setRelationshipType(request.getRelationshipType().toUpperCase());
        rel.setCreatedByUserId(actorId);

        return relationshipRepository.save(rel);
    }

    @Transactional
    public void deleteRelationship(Long relationshipId, Long actorId) {
        AdvancedTaskRelationship rel = relationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship not found with ID: " + relationshipId));
        rel.setDeleted(true);
        relationshipRepository.save(rel);
    }

    @Transactional(readOnly = true)
    public List<AdvancedTaskRelationship> getBlockedTasks(Long taskId) {
        return relationshipRepository.findBySourceTaskIdAndRelationshipTypeAndIsDeletedFalse(taskId, "BLOCKS");
    }

    @Transactional(readOnly = true)
    public List<AdvancedTaskRelationship> getBlockingTasks(Long taskId) {
        return relationshipRepository.findByTargetTaskIdAndRelationshipTypeAndIsDeletedFalse(taskId, "BLOCKS");
    }

    private Task findTaskOrThrow(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        if (task.isDeleted()) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }
        return task;
    }
}
