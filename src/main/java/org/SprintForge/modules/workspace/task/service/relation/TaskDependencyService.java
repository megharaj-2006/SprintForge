package org.SprintForge.modules.workspace.task.service.relation;

import org.SprintForge.modules.workspace.task.dto.request.CreateTaskDependencyRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskDependencyResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;

import java.util.List;

public interface TaskDependencyService {

    TaskDependencyResponse addDependency(CreateTaskDependencyRequest request, Long actorId);

    void removeDependency(Long dependencyId, Long actorId);

    List<TaskDependencyResponse> getDependencies(Long taskId, Long actorId);

    List<TaskResponse> getBlockingTasks(Long taskId, Long actorId);

    List<TaskResponse> getDependentTasks(Long taskId, Long actorId);

    boolean hasDependencies(Long taskId);

    void validateDependencies(Long taskId);

    boolean canStartTask(Long taskId);

    boolean hasBlockingDependencies(Long taskId);

    List<TaskDependencyResponse> getDependencyGraph(Long taskId, Long actorId);

    long countDependencies(Long taskId);
}
