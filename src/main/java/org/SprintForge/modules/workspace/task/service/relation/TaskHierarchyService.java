package org.SprintForge.modules.workspace.task.service.relation;

import org.SprintForge.modules.workspace.task.dto.request.CreateSubtaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.SubtaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskHierarchyResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;

import java.util.List;

public interface TaskHierarchyService {

    SubtaskResponse createSubtask(Long parentTaskId, CreateSubtaskRequest request, Long actorId);

    SubtaskResponse moveSubtask(Long taskId, Long parentTaskId, Long actorId);

    void removeParent(Long taskId, Long actorId);

    TaskResponse getParentTask(Long taskId, Long actorId);

    List<SubtaskResponse> getSubtasks(Long taskId, Long actorId);

    List<TaskResponse> getRootTasks(Long projectId, Long actorId);

    TaskHierarchyResponse getTaskHierarchy(Long taskId, Long actorId);

    boolean hasChildren(Long taskId);

    long countSubtasks(Long taskId);
}
