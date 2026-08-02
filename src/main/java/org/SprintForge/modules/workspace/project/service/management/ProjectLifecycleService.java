package org.SprintForge.modules.workspace.project.service.management;

import org.SprintForge.modules.workspace.project.dto.request.ProjectCreateRequest;
import org.SprintForge.modules.workspace.project.dto.request.ProjectUpdateRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectResponse;

public interface ProjectLifecycleService {

    ProjectResponse createProject(Long workspaceId, ProjectCreateRequest request, Long actorId);

    ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request, Long actorId);

    ProjectResponse archiveProject(Long projectId, Long actorId);

    ProjectResponse restoreProject(Long projectId, Long actorId);

    void deleteProject(Long projectId, Long actorId);

    ProjectResponse duplicateProject(Long projectId, Long actorId);

    ProjectResponse transferOwnership(Long projectId, Long newOwnerId, Long actorId);

    ProjectResponse changeLead(Long projectId, Long newLeadId, Long actorId);

    ProjectResponse activateProject(Long projectId, Long actorId);

    ProjectResponse pauseProject(Long projectId, Long actorId);

    ProjectResponse resumeProject(Long projectId, Long actorId);

    ProjectResponse completeProject(Long projectId, Boolean confirmOpenTasksOverride, Long actorId);

    ProjectResponse cancelProject(Long projectId, Long actorId);
}
