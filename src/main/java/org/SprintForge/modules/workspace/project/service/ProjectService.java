package org.SprintForge.modules.workspace.project.service;

import org.SprintForge.modules.workspace.project.dto.request.*;
import org.SprintForge.modules.workspace.project.dto.response.*;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;

import java.util.List;

public interface ProjectService {

    // Lifecycle
    ProjectResponse createProject(Long workspaceId, ProjectCreateRequest request, Long actorId);
    ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request, Long actorId);
    ProjectResponse archiveProject(Long projectId, Long actorId);
    ProjectResponse restoreProject(Long projectId, Long actorId);
    void deleteProject(Long projectId, Long actorId);
    ProjectResponse duplicateProject(Long projectId, Long actorId);
    ProjectResponse transferOwnership(Long projectId, Long newOwnerId, Long actorId);

    // Query
    List<ProjectResponse> getProjects(Long workspaceId, Long actorId);
    List<ProjectResponse> getArchivedProjects(Long workspaceId, Long actorId);
    ProjectResponse getProject(Long projectId, Long actorId);

    // Settings
    ProjectSettingsResponse getSettings(Long projectId, Long actorId);
    ProjectSettingsResponse updateSettings(Long projectId, ProjectSettingsRequest request, Long actorId);
    ProjectResponse changeVisibility(Long projectId, ProjectVisibility visibility, Long actorId);
    ProjectResponse changeColor(Long projectId, String color, Long actorId);
    ProjectResponse changeIcon(Long projectId, String icon, Long actorId);
    ProjectResponse updateProjectKey(Long projectId, String key, Long actorId);

    // Member Management
    ProjectMemberResponse addMember(Long projectId, AddProjectMemberRequest request, Long actorId);
    List<ProjectMemberResponse> getMembers(Long projectId, Long actorId);
    void removeMember(Long projectId, Long memberId, Long actorId);
    ProjectMemberResponse changeRole(Long projectId, Long memberId, UpdateProjectMemberRoleRequest request, Long actorId);
    void leaveProject(Long projectId, Long actorId);

    // Milestones
    MilestoneResponse createMilestone(Long projectId, MilestoneCreateRequest request, Long actorId);
    MilestoneResponse updateMilestone(Long milestoneId, MilestoneUpdateRequest request, Long actorId);
    MilestoneResponse archiveMilestone(Long milestoneId, Long actorId);
    void deleteMilestone(Long milestoneId, Long actorId);
    MilestoneResponse completeMilestone(Long milestoneId, Long actorId);
    MilestoneResponse assignTask(Long milestoneId, Long taskId, Long actorId);
    MilestoneResponse removeTask(Long milestoneId, Long taskId, Long actorId);
    List<MilestoneResponse> getProjectMilestones(Long projectId, Long actorId);
    List<MilestoneResponse> getOverdueMilestones(Long projectId, Long actorId);
    MilestoneProgressResponse calculateProgress(Long milestoneId, Long actorId);
}
