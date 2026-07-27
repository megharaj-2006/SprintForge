package org.SprintForge.modules.workspace.task.service.label;

import org.SprintForge.modules.workspace.task.dto.request.AssignLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.RemoveLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskLabelResponse;

import java.util.List;
import java.util.Set;

public interface TaskLabelService {

    void assignLabel(AssignLabelRequest request, Long actorId);

    void assignLabels(Long taskId, List<Long> labelIds, Long actorId);

    void removeLabel(RemoveLabelRequest request, Long actorId);

    void removeAllLabels(Long taskId, Long actorId);

    List<TaskLabelResponse> getTaskLabels(Long taskId);

    List<Long> getTaskIdsByLabel(Long labelId);

    boolean hasLabel(Long taskId, Long labelId);

}