package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistResponse;
import org.SprintForge.modules.workspace.task.dto.response.ChecklistItemResponse;
import org.SprintForge.modules.workspace.task.entity.Checklist;
import org.SprintForge.modules.workspace.task.entity.ChecklistItem;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.event.*;
import org.SprintForge.modules.workspace.task.mapper.ChecklistMapper;
import org.SprintForge.modules.workspace.task.repository.ChecklistItemRepository;
import org.SprintForge.modules.workspace.task.repository.ChecklistRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final TaskRepository taskRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final ChecklistMapper checklistMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ChecklistResponse createChecklist(Long taskId, CreateChecklistRequest request, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskNotArchived(task);

        List<Checklist> existing = checklistRepository.findByTaskIdAndIsDeletedFalseOrderByPositionAsc(taskId);
        int newPosition = existing.size();

        Checklist checklist = new Checklist();
        checklist.setTaskId(taskId);
        checklist.setTitle(request.getTitle());
        checklist.setPosition(newPosition);
        checklist.setCreatedBy(actorId.toString());

        Checklist saved = checklistRepository.save(checklist);
        eventPublisher.publishEvent(new ChecklistCreatedEvent(saved.getId(), actorId, LocalDateTime.now()));

        return checklistMapper.toResponse(saved, Collections.emptyList());
    }

    @Override
    @Transactional
    public ChecklistResponse updateChecklist(Long id, UpdateChecklistRequest request, Long actorId) {
        Checklist checklist = getChecklistOrThrow(id);
        Task task = getTaskOrThrow(checklist.getTaskId());
        validateTaskNotArchived(task);

        checklist.setTitle(request.getTitle());
        checklist.setUpdatedBy(actorId.toString());
        Checklist saved = checklistRepository.save(checklist);

        List<ChecklistItem> items = checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(saved.getId());
        List<ChecklistItemResponse> itemResponses = checklistMapper.toResponseList(items);

        return checklistMapper.toResponse(saved, itemResponses);
    }

    @Override
    @Transactional
    public void deleteChecklist(Long id, Long actorId) {
        Checklist checklist = getChecklistOrThrow(id);
        Task task = getTaskOrThrow(checklist.getTaskId());
        validateTaskNotArchived(task);

        checklist.markDeleted(actorId.toString());
        checklistRepository.save(checklist);

        // Soft delete all items inside the checklist
        List<ChecklistItem> items = checklistItemRepository.findByChecklistIdAndIsDeletedFalse(id);
        for (ChecklistItem item : items) {
            item.markDeleted(actorId.toString());
            checklistItemRepository.save(item);
        }

        // Maintain consistent checklist ordering
        List<Checklist> remaining = checklistRepository.findByTaskIdAndIsDeletedFalseOrderByPositionAsc(task.getId());
        for (int i = 0; i < remaining.size(); i++) {
            Checklist ch = remaining.get(i);
            ch.setPosition(i);
            ch.setUpdatedBy(actorId.toString());
            checklistRepository.save(ch);
        }

        eventPublisher.publishEvent(new ChecklistDeletedEvent(id, actorId, LocalDateTime.now()));

        // Update task progress percentage since checklist items are gone
        updateTaskProgress(task);
    }

    @Override
    @Transactional
    public ChecklistItemResponse addItem(Long checklistId, CreateChecklistItemRequest request, Long actorId) {
        Checklist checklist = getChecklistOrThrow(checklistId);
        Task task = getTaskOrThrow(checklist.getTaskId());
        validateTaskNotArchived(task);

        List<ChecklistItem> existing = checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(checklistId);
        int newPosition = existing.size();

        ChecklistItem item = new ChecklistItem();
        item.setChecklistId(checklistId);
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setCompleted(false);
        item.setPosition(newPosition);
        item.setCreatedBy(actorId.toString());

        ChecklistItem saved = checklistItemRepository.save(item);
        eventPublisher.publishEvent(new ChecklistItemAddedEvent(saved.getId(), actorId, LocalDateTime.now()));

        // Adding an uncompleted item updates the total checklist count, affecting progress
        updateTaskProgress(task);

        return checklistMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ChecklistItemResponse updateItem(Long itemId, UpdateChecklistItemRequest request, Long actorId) {
        ChecklistItem item = getChecklistItemOrThrow(itemId);
        Checklist checklist = getChecklistOrThrow(item.getChecklistId());
        Task task = getTaskOrThrow(checklist.getTaskId());
        validateTaskNotArchived(task);

        if (request.getTitle() != null) {
            item.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }
        item.setUpdatedBy(actorId.toString());

        ChecklistItem saved = checklistItemRepository.save(item);
        return checklistMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ChecklistItemResponse completeItem(Long itemId, Boolean completed, Long actorId) {
        ChecklistItem item = getChecklistItemOrThrow(itemId);
        Checklist checklist = getChecklistOrThrow(item.getChecklistId());
        Task task = getTaskOrThrow(checklist.getTaskId());
        validateTaskNotArchived(task);

        boolean isCompleted = Boolean.TRUE.equals(completed);
        item.setCompleted(isCompleted);
        if (isCompleted) {
            item.setCompletedBy(actorId);
            item.setCompletedAt(LocalDateTime.now());
        } else {
            item.setCompletedBy(null);
            item.setCompletedAt(null);
        }
        item.setUpdatedBy(actorId.toString());

        ChecklistItem saved = checklistItemRepository.save(item);
        eventPublisher.publishEvent(new ChecklistItemCompletedEvent(saved.getId(), isCompleted, actorId, LocalDateTime.now()));

        // Toggle of completed status updates task progress percentage
        updateTaskProgress(task);

        return checklistMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void reorderItems(List<MoveChecklistItemRequest> requests, Long actorId) {
        if (requests == null || requests.isEmpty()) {
            throw new BusinessRuleException("Reorder request cannot be empty.");
        }

        // 1. Fetch first item to identify the checklist
        ChecklistItem firstRequestItem = getChecklistItemOrThrow(requests.getFirst().getItemId());
        Long checklistId = firstRequestItem.getChecklistId();
        Checklist checklist = getChecklistOrThrow(checklistId);
        Task task = getTaskOrThrow(checklist.getTaskId());
        validateTaskNotArchived(task);

        // 2. Fetch all existing items for this checklist in DB
        List<ChecklistItem> dbItems = checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(checklistId);
        if (dbItems.isEmpty()) {
            throw new BusinessRuleException("Empty checklist cannot be reordered.");
        }

        // 3. Prevent duplicate positions and validate requests match DB items
        Set<Long> requestItemIds = new HashSet<>();
        Set<Integer> targetPositions = new HashSet<>();

        for (MoveChecklistItemRequest req : requests) {
            if (!requestItemIds.add(req.getItemId())) {
                throw new BusinessRuleException("Duplicate item ID in reorder request: " + req.getItemId());
            }
            if (!targetPositions.add(req.getPosition())) {
                throw new BusinessRuleException("Duplicate position in reorder request: " + req.getPosition());
            }
        }

        if (requests.size() != dbItems.size()) {
            throw new BusinessRuleException("Reorder request must include all items in the checklist.");
        }

        Map<Long, ChecklistItem> dbItemsMap = dbItems.stream()
                .collect(Collectors.toMap(ChecklistItem::getId, item -> item));

        for (MoveChecklistItemRequest req : requests) {
            ChecklistItem item = dbItemsMap.get(req.getItemId());
            if (item == null) {
                throw new BusinessRuleException("Item " + req.getItemId() + " does not belong to checklist " + checklistId);
            }
            // Check position is in valid range 0 to N-1
            if (req.getPosition() < 0 || req.getPosition() >= dbItems.size()) {
                throw new BusinessRuleException("Position " + req.getPosition() + " is out of bounds.");
            }
        }

        // 4. Update and save positions
        for (MoveChecklistItemRequest req : requests) {
            ChecklistItem item = dbItemsMap.get(req.getItemId());
            item.setPosition(req.getPosition());
            item.setUpdatedBy(actorId.toString());
            checklistItemRepository.save(item);
        }
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId, Long actorId) {
        ChecklistItem item = getChecklistItemOrThrow(itemId);
        Checklist checklist = getChecklistOrThrow(item.getChecklistId());
        Task task = getTaskOrThrow(checklist.getTaskId());
        validateTaskNotArchived(task);

        item.markDeleted(actorId.toString());
        checklistItemRepository.save(item);

        // Reorder remaining items to keep positions consistent
        List<ChecklistItem> remaining = checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(checklist.getId());
        for (int i = 0; i < remaining.size(); i++) {
            ChecklistItem it = remaining.get(i);
            it.setPosition(i);
            it.setUpdatedBy(actorId.toString());
            checklistItemRepository.save(it);
        }

        eventPublisher.publishEvent(new ChecklistItemRemovedEvent(itemId, actorId, LocalDateTime.now()));

        // Removing an item changes the task progress calculation
        updateTaskProgress(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChecklistResponse> getTaskChecklists(Long taskId, Long actorId) {
        Task task = getTaskOrThrow(taskId);

        List<Checklist> checklists = checklistRepository.findByTaskIdAndIsDeletedFalseOrderByPositionAsc(taskId);
        List<ChecklistResponse> responses = new ArrayList<>();

        for (Checklist cl : checklists) {
            List<ChecklistItem> items = checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(cl.getId());
            List<ChecklistItemResponse> itemResponses = checklistMapper.toResponseList(items);
            responses.add(checklistMapper.toResponse(cl, itemResponses));
        }

        return responses;
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
    }

    private Checklist getChecklistOrThrow(Long id) {
        return checklistRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist not found with ID: " + id));
    }

    private ChecklistItem getChecklistItemOrThrow(Long id) {
        return checklistItemRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist item not found with ID: " + id));
    }

    private void validateTaskNotArchived(Task task) {
        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }
    }

    private void updateTaskProgress(Task task) {
        List<Checklist> checklists = checklistRepository.findByTaskIdAndIsDeletedFalseOrderByPositionAsc(task.getId());
        int totalItems = 0;
        int completedItems = 0;

        for (Checklist cl : checklists) {
            List<ChecklistItem> items = checklistItemRepository.findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(cl.getId());
            for (ChecklistItem item : items) {
                totalItems++;
                if (Boolean.TRUE.equals(item.getCompleted())) {
                    completedItems++;
                }
            }
        }

        double progress = 0.0;
        if (totalItems > 0) {
            progress = (completedItems * 100.0) / totalItems;
        }

        task.setProgressPercentage(progress);
        taskRepository.save(task);
    }
}
