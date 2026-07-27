package org.SprintForge.modules.workspace.project.service.query;

import org.SprintForge.modules.workspace.project.dto.request.ProjectSearchRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectQueryService {

    ProjectResponse getProject(Long id, Long actorId);

    List<ProjectResponse> getProjects(Long workspaceId, Long actorId);

    List<ProjectResponse> getArchivedProjects(Long workspaceId, Long actorId);

    List<ProjectResponse> searchProjects(ProjectSearchRequest request, Long actorId);

    List<ProjectResponse> getRecentProjects(Long userId, int limit, Long actorId);
}
