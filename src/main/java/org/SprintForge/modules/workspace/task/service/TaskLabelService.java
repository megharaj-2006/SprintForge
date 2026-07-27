package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;

import java.util.List;

public interface TaskLabelService {

    void assignLabel(Long taskId, Long labelId, Long actorId);

    void assignLabels(Long taskId, List<Long> labelIds, Long actorId);

    void removeLabel(Long taskId, Long labelId, Long actorId);

    void removeAllLabels(Long taskId, Long actorId);

    List<LabelResponse> getTaskLabels(Long taskId, Long actorId);

    List<TaskResponse> getTasksByLabel(Long labelId, Long actorId);

    boolean hasLabel(Long taskId, Long labelId);

    long countTasksUsingLabel(Long labelId);
}
