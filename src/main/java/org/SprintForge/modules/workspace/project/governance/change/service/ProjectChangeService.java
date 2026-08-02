package org.SprintForge.modules.workspace.project.governance.change.service;

import org.SprintForge.modules.workspace.project.governance.change.dto.request.CreateChangeRequest;
import org.SprintForge.modules.workspace.project.governance.change.dto.request.UpdateChangeRequest;
import org.SprintForge.modules.workspace.project.governance.change.dto.response.ChangeResponse;

import java.util.List;

public interface ProjectChangeService {
    ChangeResponse createChangeRequest(Long projectId, CreateChangeRequest request, Long actorId);
    ChangeResponse updateChangeRequest(Long changeId, UpdateChangeRequest request, Long actorId);
    List<ChangeResponse> getProjectChanges(Long projectId);
    ChangeResponse getChange(Long changeId);
    void deleteChange(Long changeId, Long actorId);
}
