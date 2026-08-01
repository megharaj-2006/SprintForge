package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.TaskPermissionOverride;
import org.SprintForge.modules.workspace.task.repository.TaskPermissionOverrideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskPermissionService {

    private final TaskPermissionOverrideRepository overrideRepository;

    @Transactional
    public TaskPermissionOverride setPermissionOverride(Long taskId, Long userId, String permission, Boolean allowed, Long actorId) {
        Optional<TaskPermissionOverride> existing = overrideRepository.findByTaskIdAndUserIdAndPermissionAndIsDeletedFalse(taskId, userId, permission);
        TaskPermissionOverride override;
        if (existing.isPresent()) {
            override = existing.get();
            override.setAllowed(allowed);
        } else {
            override = new TaskPermissionOverride();
            override.setTaskId(taskId);
            override.setUserId(userId);
            override.setPermission(permission.toUpperCase());
            override.setAllowed(allowed);
            override.setCreatedByUserId(actorId);
        }
        return overrideRepository.save(override);
    }

    @Transactional(readOnly = true)
    public List<TaskPermissionOverride> getTaskOverrides(Long taskId) {
        return overrideRepository.findByTaskIdAndIsDeletedFalse(taskId);
    }

    @Transactional(readOnly = true)
    public boolean hasPermission(Long taskId, Long userId, String permission) {
        Optional<TaskPermissionOverride> override = overrideRepository.findByTaskIdAndUserIdAndPermissionAndIsDeletedFalse(taskId, userId, permission.toUpperCase());
        if (override.isPresent()) {
            return Boolean.TRUE.equals(override.get().getAllowed());
        }
        return true; // Default fallback to role-based permission
    }

    @Transactional
    public void removePermissionOverride(Long overrideId) {
        TaskPermissionOverride override = overrideRepository.findById(overrideId).orElse(null);
        if (override != null) {
            override.setDeleted(true);
            overrideRepository.save(override);
        }
    }
}
