package org.SprintForge.modules.workspace.task.service.management;

import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.DuplicateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;

public interface TaskLifecycleService {

    TaskResponse createTask(CreateTaskRequest request, Long actorId);

    TaskResponse updateTask(Long id, UpdateTaskRequest request, Long actorId);

    void deleteTask(Long id, Long actorId);

    TaskResponse archiveTask(Long id, Long actorId);

    TaskResponse restoreTask(Long id, Long actorId);

    TaskResponse duplicateTask(Long id, DuplicateTaskRequest request, Long actorId);

    TaskResponse moveTaskToSprint(Long id, Long sprintId, Long actorId);

    TaskResponse removeFromSprint(Long id, Long actorId);
}
