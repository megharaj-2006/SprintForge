package org.SprintForge.modules.workspace.project.service.settings;

import org.SprintForge.modules.workspace.project.dto.request.ProjectSettingsRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectResponse;
import org.SprintForge.modules.workspace.project.dto.response.ProjectSettingsResponse;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;

public interface ProjectSettingsService {

    ProjectSettingsResponse getSettings(Long projectId, Long actorId);

    ProjectSettingsResponse updateSettings(Long projectId, ProjectSettingsRequest request, Long actorId);

    ProjectResponse changeVisibility(Long projectId, ProjectVisibility visibility, Long actorId);

    ProjectResponse changeColor(Long projectId, String color, Long actorId);

    ProjectResponse changeIcon(Long projectId, String icon, Long actorId);

    ProjectResponse updateProjectKey(Long projectId, String newKey, Long actorId);
}
