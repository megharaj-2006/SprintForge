package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.task.dto.request.AddWatcherRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskWatcherResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskWatcher;
import org.SprintForge.modules.workspace.task.event.TaskWatcherAddedEvent;
import org.SprintForge.modules.workspace.task.event.TaskWatcherRemovedEvent;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.mapper.TaskWatcherMapper;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.task.repository.TaskWatcherRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskWatcherServiceImpl implements TaskWatcherService {

    private final TaskWatcherRepository taskWatcherRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskWatcherMapper taskWatcherMapper;
    private final TaskMapper taskMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TaskWatcherResponse addWatcher(Long taskId, AddWatcherRequest request, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskNotArchived(task);

        Long userId = request.getUserId();
        User user = getUserOrThrow(userId);

        Optional<TaskWatcher> existingOpt = taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(taskId, userId);
        if (existingOpt.isPresent()) {
            throw new BusinessRuleException("User is already watching this task.");
        }

        // Check if there is a soft-deleted watcher to restore, or create new
        // Note: soft-delete is managed manually, so we query taskWatcherRepository directly.
        // Wait, JpaRepository findByIdAndIsDeletedFalse is our standard, but since JpaRepository doesn't filter findByTaskIdAndUserId unless we implement findByTaskIdAndUserIdAndIsDeletedTrue/False,
        // let's just find any entry (including deleted ones) or clean up and insert.
        // To be safe, let's query all including deleted:
        // Actually, we can just write a query or use findByTaskIdAndUserIdAndIsDeletedFalse/True.
        // Or simply search for any existing record in DB:
        TaskWatcher watcher = taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(taskId, userId).orElse(null);
        if (watcher == null) {
            // Find any deleted one if we want to restore
            // But we can also just create a new one! Creating a new one is extremely simple and robust.
            watcher = new TaskWatcher();
            watcher.setTaskId(taskId);
            watcher.setUserId(userId);
        }

        watcher.setWatchingSince(LocalDateTime.now());
        watcher.setNotificationPreference(request.getNotificationPreference() != null ? request.getNotificationPreference() : "ALL");
        watcher.setCreatedBy(actorId.toString());
        watcher.setDeleted(false); // Make sure it's not deleted

        TaskWatcher saved = taskWatcherRepository.save(watcher);
        eventPublisher.publishEvent(new TaskWatcherAddedEvent(saved.getId(), taskId, userId, actorId, LocalDateTime.now()));

        return taskWatcherMapper.toResponse(saved, user.getUsername());
    }

    @Override
    @Transactional
    public void removeWatcher(Long taskId, Long userId, Long actorId) {
        getTaskOrThrow(taskId);
        TaskWatcher watcher = taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Watcher not found for this task and user."));

        watcher.markDeleted(actorId.toString());
        taskWatcherRepository.save(watcher);

        eventPublisher.publishEvent(new TaskWatcherRemovedEvent(watcher.getId(), taskId, userId, actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public TaskWatcherResponse toggleWatcher(Long taskId, Long actorId) {
        getTaskOrThrow(taskId);
        Optional<TaskWatcher> watcherOpt = taskWatcherRepository.findByTaskIdAndUserIdAndIsDeletedFalse(taskId, actorId);

        if (watcherOpt.isPresent()) {
            removeWatcher(taskId, actorId, actorId);
            return null;
        } else {
            AddWatcherRequest request = AddWatcherRequest.builder()
                    .userId(actorId)
                    .notificationPreference("ALL")
                    .build();
            return addWatcher(taskId, request, actorId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskWatcherResponse> getTaskWatchers(Long taskId, Long actorId) {
        getTaskOrThrow(taskId);
        List<TaskWatcher> watchers = taskWatcherRepository.findByTaskIdAndIsDeletedFalse(taskId);
        List<TaskWatcherResponse> responses = new ArrayList<>();

        for (TaskWatcher w : watchers) {
            String username = userRepository.findById(w.getUserId())
                    .map(User::getUsername)
                    .orElse("Unknown");
            responses.add(taskWatcherMapper.toResponse(w, username));
        }

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isWatching(Long taskId, Long userId) {
        return taskWatcherRepository.existsByTaskIdAndUserIdAndIsDeletedFalse(taskId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getWatchingTasks(Long userId, Long actorId) {
        getUserOrThrow(userId);
        List<Task> tasks = taskWatcherRepository.findWatchingTasks(userId);
        return taskMapper.toResponseList(tasks);
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    private void validateTaskNotArchived(Task task) {
        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }
    }
}
