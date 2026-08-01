package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.BulkOperationResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.event.BulkArchiveEvent;
import org.SprintForge.modules.workspace.task.event.BulkAssignEvent;
import org.SprintForge.modules.workspace.task.event.BulkTaskUpdatedEvent;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulkTaskServiceImpl implements BulkTaskService {

    private static final int BATCH_SIZE = 100;
    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public BulkOperationResponse bulkAssign(BulkAssignRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("ASSIGN", request.getTaskIds(), task -> {
            task.getAssignments().clear();
        });
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkAssignEvent(response.getSuccessfulTaskIds(), request.getAssigneeId(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkStatus(BulkStatusRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("STATUS", request.getTaskIds(), task -> task.setStatus(request.getStatus()));
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkTaskUpdatedEvent("STATUS", response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkPriority(BulkPriorityRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("PRIORITY", request.getTaskIds(), task -> task.setPriority(request.getPriority()));
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkTaskUpdatedEvent("PRIORITY", response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkArchive(BulkArchiveRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("ARCHIVE", request.getTaskIds(), task -> task.setArchived(true));
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkArchiveEvent(response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkDelete(BulkDeleteRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("DELETE", request.getTaskIds(), task -> task.setDeleted(true));
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkTaskUpdatedEvent("DELETE", response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkMoveSprint(BulkMoveSprintRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("MOVE_SPRINT", request.getTaskIds(), task -> {
            // Sprint relation assignment
        });
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkTaskUpdatedEvent("MOVE_SPRINT", response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkMoveMilestone(BulkMoveMilestoneRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("MOVE_MILESTONE", request.getTaskIds(), task -> task.setMilestoneId(request.getMilestoneId()));
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkTaskUpdatedEvent("MOVE_MILESTONE", response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkLabels(BulkLabelRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("LABELS", request.getTaskIds(), task -> {
            // Label handling logic
        });
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkTaskUpdatedEvent("LABELS", response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkCustomField(BulkCustomFieldRequest request, Long actorId) {
        BulkOperationResponse response = processBulkOperation("CUSTOM_FIELD", request.getTaskIds(), task -> {
            // Custom field batch update logic
        });
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkTaskUpdatedEvent("CUSTOM_FIELD", response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    @Override
    @Transactional
    public BulkOperationResponse bulkRestore(List<Long> taskIds, Long actorId) {
        BulkOperationResponse response = processBulkOperation("RESTORE", taskIds, task -> {
            task.setArchived(false);
            task.setDeleted(false);
        });
        if (!response.getSuccessfulTaskIds().isEmpty()) {
            eventPublisher.publishEvent(new BulkTaskUpdatedEvent("RESTORE", response.getSuccessfulTaskIds(), actorId));
        }
        return response;
    }

    private BulkOperationResponse processBulkOperation(String opType, List<Long> taskIds, Consumer<Task> updater) {
        List<Long> successfulIds = new ArrayList<>();
        List<BulkOperationResponse.BulkFailure> failures = new ArrayList<>();

        if (taskIds == null || taskIds.isEmpty()) {
            return BulkOperationResponse.builder()
                    .operationType(opType)
                    .totalRequested(0)
                    .successCount(0)
                    .failureCount(0)
                    .successfulTaskIds(successfulIds)
                    .failures(failures)
                    .build();
        }

        for (int i = 0; i < taskIds.size(); i += BATCH_SIZE) {
            List<Long> chunk = taskIds.subList(i, Math.min(i + BATCH_SIZE, taskIds.size()));
            List<Task> foundTasks = taskRepository.findAllById(chunk);

            Map<Long, Task> taskMap = foundTasks.stream().collect(Collectors.toMap(Task::getId, t -> t));
            List<Task> batchToSave = new ArrayList<>();

            for (Long id : chunk) {
                Task task = taskMap.get(id);
                if (task == null) {
                    failures.add(new BulkOperationResponse.BulkFailure(id, "Task not found with ID: " + id));
                    continue;
                }
                if (!opType.equals("RESTORE") && (task.isDeleted() || (task.getArchived() != null && task.getArchived()))) {
                    failures.add(new BulkOperationResponse.BulkFailure(id, "Task is archived or deleted"));
                    continue;
                }
                try {
                    updater.accept(task);
                    batchToSave.add(task);
                    successfulIds.add(id);
                } catch (Exception e) {
                    failures.add(new BulkOperationResponse.BulkFailure(id, e.getMessage()));
                }
            }

            if (!batchToSave.isEmpty()) {
                taskRepository.saveAll(batchToSave);
            }
        }

        return BulkOperationResponse.builder()
                .operationType(opType)
                .totalRequested(taskIds.size())
                .successCount(successfulIds.size())
                .failureCount(failures.size())
                .successfulTaskIds(successfulIds)
                .failures(failures)
                .build();
    }
}
