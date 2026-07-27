package org.SprintForge.modules.workspace.task.service;

import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;

import java.util.List;

public interface LabelManagementService {

    LabelResponse createLabel(Long projectId, CreateLabelRequest request, Long actorId);

    LabelResponse updateLabel(Long labelId, UpdateLabelRequest request, Long actorId);

    LabelResponse archiveLabel(Long labelId, Long actorId);

    LabelResponse restoreLabel(Long labelId, Long actorId);

    void deleteLabel(Long labelId, Long actorId);

    List<LabelResponse> getLabels(Long projectId, Long actorId);

    List<LabelResponse> searchLabels(Long projectId, String query, Long actorId);
}
