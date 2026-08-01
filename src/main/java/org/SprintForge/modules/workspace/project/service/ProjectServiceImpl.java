package org.SprintForge.modules.workspace.project.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.dto.request.*;
import org.SprintForge.modules.workspace.project.dto.response.*;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;
import org.SprintForge.modules.workspace.project.service.management.ProjectLifecycleService;
import org.SprintForge.modules.workspace.project.service.member.ProjectMemberService;
import org.SprintForge.modules.workspace.project.service.milestone.MilestoneService;
import org.SprintForge.modules.workspace.project.service.query.ProjectQueryService;
import org.SprintForge.modules.workspace.project.service.settings.ProjectSettingsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectLifecycleService projectLifecycleService;
    private final ProjectQueryService projectQueryService;
    private final ProjectSettingsService projectSettingsService;
    private final ProjectMemberService projectMemberService;
    private final MilestoneService milestoneService;

    // Lifecycle
    @Override
    public ProjectResponse createProject(Long workspaceId, ProjectCreateRequest request, Long actorId) {
        return projectLifecycleService.createProject(workspaceId, request, actorId);
    }

    @Override
    public ProjectResponse updateProject(Long projectId, ProjectUpdateRequest request, Long actorId) {
        return projectLifecycleService.updateProject(projectId, request, actorId);
    }

    @Override
    public ProjectResponse archiveProject(Long projectId, Long actorId) {
        return projectLifecycleService.archiveProject(projectId, actorId);
    }

    @Override
    public ProjectResponse restoreProject(Long projectId, Long actorId) {
        return projectLifecycleService.restoreProject(projectId, actorId);
    }

    @Override
    public void deleteProject(Long projectId, Long actorId) {
        projectLifecycleService.deleteProject(projectId, actorId);
    }

    @Override
    public ProjectResponse duplicateProject(Long projectId, Long actorId) {
        return projectLifecycleService.duplicateProject(projectId, actorId);
    }

    @Override
    public ProjectResponse transferOwnership(Long projectId, Long newOwnerId, Long actorId) {
        return projectLifecycleService.transferOwnership(projectId, newOwnerId, actorId);
    }

    // Query
    @Override
    public List<ProjectResponse> getProjects(Long workspaceId, Long actorId) {
        return projectQueryService.getProjects(workspaceId, actorId);
    }

    @Override
    public List<ProjectResponse> getArchivedProjects(Long workspaceId, Long actorId) {
        return projectQueryService.getArchivedProjects(workspaceId, actorId);
    }

    @Override
    public ProjectResponse getProject(Long projectId, Long actorId) {
        return projectQueryService.getProject(projectId, actorId);
    }

    // Settings
    @Override
    public ProjectSettingsResponse getSettings(Long projectId, Long actorId) {
        return projectSettingsService.getSettings(projectId, actorId);
    }

    @Override
    public ProjectSettingsResponse updateSettings(Long projectId, ProjectSettingsRequest request, Long actorId) {
        return projectSettingsService.updateSettings(projectId, request, actorId);
    }

    @Override
    public ProjectResponse changeVisibility(Long projectId, ProjectVisibility visibility, Long actorId) {
        return projectSettingsService.changeVisibility(projectId, visibility, actorId);
    }

    @Override
    public ProjectResponse changeColor(Long projectId, String color, Long actorId) {
        return projectSettingsService.changeColor(projectId, color, actorId);
    }

    @Override
    public ProjectResponse changeIcon(Long projectId, String icon, Long actorId) {
        return projectSettingsService.changeIcon(projectId, icon, actorId);
    }

    @Override
    public ProjectResponse updateProjectKey(Long projectId, String key, Long actorId) {
        return projectSettingsService.updateProjectKey(projectId, key, actorId);
    }

    // Member Management
    @Override
    public ProjectMemberResponse addMember(Long projectId, AddProjectMemberRequest request, Long actorId) {
        return projectMemberService.addMember(projectId, request, actorId);
    }

    @Override
    public List<ProjectMemberResponse> getMembers(Long projectId, Long actorId) {
        return projectMemberService.getMembers(projectId, actorId);
    }

    @Override
    public void removeMember(Long projectId, Long memberId, Long actorId) {
        projectMemberService.removeMember(projectId, memberId, actorId);
    }

    @Override
    public ProjectMemberResponse changeRole(Long projectId, Long memberId, UpdateProjectMemberRoleRequest request, Long actorId) {
        return projectMemberService.changeRole(projectId, memberId, request, actorId);
    }

    @Override
    public void leaveProject(Long projectId, Long actorId) {
        projectMemberService.leaveProject(projectId, actorId);
    }

    // Milestones
    @Override
    public MilestoneResponse createMilestone(Long projectId, MilestoneCreateRequest request, Long actorId) {
        return milestoneService.createMilestone(projectId, request, actorId);
    }

    @Override
    public MilestoneResponse updateMilestone(Long milestoneId, MilestoneUpdateRequest request, Long actorId) {
        return milestoneService.updateMilestone(milestoneId, request, actorId);
    }

    @Override
    public MilestoneResponse archiveMilestone(Long milestoneId, Long actorId) {
        return milestoneService.archiveMilestone(milestoneId, actorId);
    }

    @Override
    public void deleteMilestone(Long milestoneId, Long actorId) {
        milestoneService.deleteMilestone(milestoneId, actorId);
    }

    @Override
    public MilestoneResponse completeMilestone(Long milestoneId, Long actorId) {
        return milestoneService.completeMilestone(milestoneId, actorId);
    }

    @Override
    public MilestoneResponse assignTask(Long milestoneId, Long taskId, Long actorId) {
        return milestoneService.assignTask(milestoneId, taskId, actorId);
    }

    @Override
    public MilestoneResponse removeTask(Long milestoneId, Long taskId, Long actorId) {
        return milestoneService.removeTask(milestoneId, taskId, actorId);
    }

    @Override
    public List<MilestoneResponse> getProjectMilestones(Long projectId, Long actorId) {
        return milestoneService.getProjectMilestones(projectId, actorId);
    }

    @Override
    public List<MilestoneResponse> getOverdueMilestones(Long projectId, Long actorId) {
        return milestoneService.getOverdueMilestones(projectId, actorId);
    }

    @Override
    public MilestoneProgressResponse calculateProgress(Long milestoneId, Long actorId) {
        return milestoneService.calculateProgress(milestoneId, actorId);
    }
}
