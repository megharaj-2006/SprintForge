package org.SprintForge.modules.workspace.task.service.management;

import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.util.List;

public interface TaskWorkflowService {

    TaskResponse changeStatus(Long id, TaskStatus status, Long actorId);

    TaskResponse changePriority(Long id, TaskPriority priority, Long actorId);

    TaskResponse changeType(Long id, TaskType type, Long actorId);

    boolean validateTransition(TaskStatus current, TaskStatus target);

    List<TaskStatus> getAllowedTransitions(Long id, Long actorId);

    TaskResponse startTask(Long id, Long actorId);

    TaskResponse sendForReview(Long id, Long actorId);

    TaskResponse completeTask(Long id, Long actorId);

    TaskResponse cancelTask(Long id, Long actorId);

    TaskResponse reopenTask(Long id, TaskStatus targetStatus, Long actorId);

    List<TaskResponse> getTasksByStatus(Long projectId, TaskStatus status, Long actorId);

    long countTasksByStatus(Long projectId, TaskStatus status, Long actorId);
}
