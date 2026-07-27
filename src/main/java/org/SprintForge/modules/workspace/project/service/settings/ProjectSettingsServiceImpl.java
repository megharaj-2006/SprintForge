package org.SprintForge.modules.workspace.project.service.settings;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.dto.request.ProjectSettingsRequest;
import org.SprintForge.modules.workspace.project.dto.response.ProjectResponse;
import org.SprintForge.modules.workspace.project.dto.response.ProjectSettingsResponse;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectSettings;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;
import org.SprintForge.modules.workspace.project.event.ProjectUpdatedEvent;
import org.SprintForge.modules.workspace.project.mapper.ProjectMapper;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectSettingsRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectSettingsServiceImpl implements ProjectSettingsService {

    private final ProjectRepository projectRepository;
    private final ProjectSettingsRepository projectSettingsRepository;
    private final WorkspacePermissionService workspacePermissionService;
    private final ProjectMapper projectMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public ProjectSettingsResponse getSettings(Long projectId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        checkAccess(project, actorId);

        ProjectSettings settings = projectSettingsRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project settings not found for project: " + projectId));

        return projectMapper.toResponse(settings);
    }

    @Override
    @Transactional
    public ProjectSettingsResponse updateSettings(Long projectId, ProjectSettingsRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        checkManagePermission(project, actorId);

        ProjectSettings settings = projectSettingsRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .orElseGet(() -> {
                    ProjectSettings newSettings = new ProjectSettings();
                    newSettings.setProjectId(projectId);
                    return newSettings;
                });

        projectMapper.updateEntity(request, settings);
        ProjectSettings saved = projectSettingsRepository.save(settings);

        eventPublisher.publishEvent(new ProjectUpdatedEvent(projectId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse changeVisibility(Long projectId, ProjectVisibility visibility, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        checkManagePermission(project, actorId);

        project.setVisibility(visibility);
        Project saved = projectRepository.save(project);

        eventPublisher.publishEvent(new ProjectUpdatedEvent(projectId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse changeColor(Long projectId, String color, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        checkManagePermission(project, actorId);

        project.setColor(color);
        Project saved = projectRepository.save(project);

        eventPublisher.publishEvent(new ProjectUpdatedEvent(projectId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse changeIcon(Long projectId, String icon, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        checkManagePermission(project, actorId);

        project.setIcon(icon);
        Project saved = projectRepository.save(project);

        eventPublisher.publishEvent(new ProjectUpdatedEvent(projectId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProjectResponse updateProjectKey(Long projectId, String newKey, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        checkManagePermission(project, actorId);

        if (newKey == null || newKey.isBlank()) {
            throw new BusinessRuleException("Project key cannot be blank.");
        }
        newKey = newKey.trim().toUpperCase();
        if (newKey.length() < 2 || newKey.length() > 10) {
            throw new BusinessRuleException("Project key must be between 2 and 10 characters.");
        }

        if (!newKey.equals(project.getProjectKey())) {
            if (projectRepository.existsByWorkspaceIdAndProjectKeyAndIsDeletedFalse(project.getWorkspaceId(), newKey)) {
                throw new ConflictException("Project key already exists in this workspace.");
            }
            project.setProjectKey(newKey);
        }

        Project saved = projectRepository.save(project);

        eventPublisher.publishEvent(new ProjectUpdatedEvent(projectId, actorId, LocalDateTime.now()));

        return projectMapper.toResponse(saved);
    }

    private void checkManagePermission(Project project, Long actorId) {
        if (Boolean.TRUE.equals(project.getIsArchived())) {
            throw new BusinessRuleException("Cannot modify settings of an archived project.");
        }

        boolean isOwner = actorId.equals(project.getOwnerId());
        boolean canManage = workspacePermissionService.canManageProjects(project.getWorkspaceId(), actorId);
        if (!isOwner && !canManage) {
            throw new ForbiddenException("Access Denied: You do not have permission to manage this project's settings.");
        }
    }

    private void checkAccess(Project project, Long actorId) {
        if (project.getVisibility() == ProjectVisibility.PUBLIC) {
            return;
        }

        if (actorId == null) {
            throw new ForbiddenException("Access Denied: Authentication required.");
        }

        boolean isOwner = actorId.equals(project.getOwnerId());
        boolean isWorkspaceAdmin = workspacePermissionService.hasPermission(project.getWorkspaceId(), actorId, "PROJECT_MANAGE");
        if (!isOwner && !isWorkspaceAdmin) {
            throw new ForbiddenException("Access Denied: You do not have permission to access this project's settings.");
        }
    }
}
