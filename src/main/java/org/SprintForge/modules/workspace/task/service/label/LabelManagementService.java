package org.SprintForge.modules.workspace.task.service.label;

import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.LabelSummaryResponse;

import java.util.List;

public interface LabelManagementService {

    LabelResponse createLabel(CreateLabelRequest request, Long actorId);

    LabelResponse updateLabel(Long labelId, UpdateLabelRequest request, Long actorId);

    void archiveLabel(Long labelId, Long actorId);

    void restoreLabel(Long labelId, Long actorId);

    void deleteLabel(Long labelId, Long actorId);

    LabelResponse getLabel(Long labelId);

    List<LabelResponse> getLabelsByProject(Long projectId);

    List<LabelSummaryResponse> searchLabels(Long projectId, String keyword);

    long countTasksUsingLabel(Long labelId);
}