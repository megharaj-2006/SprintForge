package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.user.entity.User;
import org.SprintForge.modules.user.repository.UserRepository;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistoryResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHistorySummaryResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskHistory;
import org.SprintForge.modules.workspace.task.entity.enums.TaskHistoryActionType;
import org.SprintForge.modules.workspace.task.mapper.TaskHistoryMapper;
import org.SprintForge.modules.workspace.task.repository.TaskHistoryRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final TaskRepository taskRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    private final UserRepository userRepository;
    private final ProjectPermissionService projectPermissionService;
    private final TaskHistoryMapper taskHistoryMapper;

    @Override
    @Transactional
    public void recordHistory(Long taskId, Long actorId, TaskHistoryActionType actionType, 
                              String fieldName, String oldValue, String newValue, String description) {
        log.info("Recording history for task: {} action: {} by: {}", taskId, actionType, actorId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        User user = null;
        if (actorId != null) {
            user = userRepository.findById(actorId).orElse(null);
        }

        TaskHistory history = new TaskHistory();
        history.setTask(task);
        history.setPerformedBy(user);
        history.setActionType(actionType);
        history.setFieldName(fieldName);
        history.setOldValue(oldValue);
        history.setNewValue(newValue);
        history.setDescription(description);
        history.setCreatedBy(actorId != null ? actorId.toString() : "SYSTEM");

        taskHistoryRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistoryResponse> getTaskHistory(Long taskId, Long actorId) {
        log.info("Retrieving history for task: {} by: {}", taskId, actorId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        if (!projectPermissionService.canViewProject(task.getProject().getId(), actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to view this project.");
        }

        List<TaskHistory> histories = taskHistoryRepository.findByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(taskId);
        return taskHistoryMapper.toResponseList(histories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskHistorySummaryResponse> getRecentActivity(Long taskId, Long actorId) {
        log.info("Retrieving recent activity summary for task: {} by: {}", taskId, actorId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        if (!projectPermissionService.canViewProject(task.getProject().getId(), actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to view this project.");
        }

        List<TaskHistory> histories = taskHistoryRepository.findByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(taskId);
        return taskHistoryMapper.toSummaryResponseList(histories);
    }

    @Override
    @Transactional
    public void deleteHistory(Long taskId, Long actorId) {
        log.info("Deleting history for task: {} by: {}", taskId, actorId);
        
        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        if (!projectPermissionService.canManageTasks(task.getProject().getId(), actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to delete history logs.");
        }

        taskHistoryRepository.deleteByTaskId(taskId, actorId.toString());
    }
}
