package org.SprintForge.modules.workspace.customfield.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.customfield.dto.request.CreateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.UpdateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.CustomFieldResponse;
import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.SprintForge.modules.workspace.customfield.entity.CustomFieldValue;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldArchivedEvent;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldCreatedEvent;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldDeletedEvent;
import org.SprintForge.modules.workspace.customfield.event.CustomFieldUpdatedEvent;
import org.SprintForge.modules.workspace.customfield.mapper.CustomFieldMapper;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldRepository;
import org.SprintForge.modules.workspace.customfield.repository.CustomFieldValueRepository;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.SprintForge.modules.workspace.project.entity.ProjectSettings;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRoleRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectSettingsRepository;
import org.SprintForge.modules.workspace.project.service.member.ProjectPermissionService;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.service.WorkspacePermissionService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomFieldManagementServiceImpl implements CustomFieldManagementService {

    private final ProjectRepository projectRepository;
    private final ProjectSettingsRepository projectSettingsRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    
    private final CustomFieldRepository customFieldRepository;
    private final CustomFieldValueRepository customFieldValueRepository;
    
    private final CustomFieldMapper customFieldMapper;
    
    private final WorkspacePermissionService workspacePermissionService;
    private final ProjectPermissionService projectPermissionService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CustomFieldResponse createField(Long projectId, CreateCustomFieldRequest request, Long actorId) {
        log.info("Creating custom field: {} for project: {} by actor: {}", request.getName(), projectId, actorId);
        
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        // Enforce permissions: Only project owner, workspace manage, or project ADMIN/MANAGER can create custom fields.
        checkManageAccess(project, actorId);

        // Check if custom fields are disabled in project settings
        ProjectSettings settings = projectSettingsRepository.findByProjectIdAndIsDeletedFalse(projectId).orElse(null);
        if (settings != null && Boolean.FALSE.equals(settings.getAllowCustomFields())) {
            throw new BusinessRuleException("Custom fields are disabled for this project.");
        }

        // Validate name uniqueness within the project
        if (customFieldRepository.existsByProjectIdAndNameAndIsDeletedFalse(projectId, request.getName())) {
            throw new ConflictException("Custom field name already exists in this project.");
        }

        CustomField field = customFieldMapper.toEntity(request);
        field.setProject(project);
        field.setArchived(false);
        field.setCreatedBy(actorId.toString());

        CustomField saved = customFieldRepository.save(field);
        
        eventPublisher.publishEvent(new CustomFieldCreatedEvent(saved.getId(), projectId, actorId, LocalDateTime.now()));
        
        return customFieldMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CustomFieldResponse updateField(Long fieldId, UpdateCustomFieldRequest request, Long actorId) {
        log.info("Updating custom field definition: {} by actor: {}", fieldId, actorId);
        
        CustomField field = customFieldRepository.findById(fieldId)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found with ID: " + fieldId));

        checkManageAccess(field.getProject(), actorId);

        // Validate name uniqueness if changing
        if (request.getName() != null && !request.getName().equals(field.getName())) {
            if (customFieldRepository.existsByProjectIdAndNameAndIsDeletedFalse(field.getProject().getId(), request.getName())) {
                throw new ConflictException("Custom field name already exists in this project.");
            }
        }

        customFieldMapper.updateEntity(request, field);
        field.setUpdatedBy(actorId.toString());
        CustomField saved = customFieldRepository.save(field);

        eventPublisher.publishEvent(new CustomFieldUpdatedEvent(saved.getId(), field.getProject().getId(), actorId, LocalDateTime.now()));

        return customFieldMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CustomFieldResponse archiveField(Long fieldId, Long actorId) {
        log.info("Archiving custom field: {} by actor: {}", fieldId, actorId);
        
        CustomField field = customFieldRepository.findById(fieldId)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found."));

        checkManageAccess(field.getProject(), actorId);

        field.setArchived(true);
        field.setUpdatedBy(actorId.toString());
        CustomField saved = customFieldRepository.save(field);

        eventPublisher.publishEvent(new CustomFieldArchivedEvent(saved.getId(), field.getProject().getId(), actorId, LocalDateTime.now()));

        return customFieldMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CustomFieldResponse restoreField(Long fieldId, Long actorId) {
        log.info("Restoring custom field: {} by actor: {}", fieldId, actorId);
        
        CustomField field = customFieldRepository.findById(fieldId)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found."));

        checkManageAccess(field.getProject(), actorId);

        field.setArchived(false);
        field.setUpdatedBy(actorId.toString());
        CustomField saved = customFieldRepository.save(field);

        eventPublisher.publishEvent(new CustomFieldUpdatedEvent(saved.getId(), field.getProject().getId(), actorId, LocalDateTime.now()));

        return customFieldMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteField(Long fieldId, Long actorId) {
        log.info("Deleting custom field: {} by actor: {}", fieldId, actorId);
        
        CustomField field = customFieldRepository.findById(fieldId)
                .filter(f -> !f.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found."));

        checkManageAccess(field.getProject(), actorId);

        // Required fields cannot be deleted while in use
        if (Boolean.TRUE.equals(field.getRequired())) {
            List<CustomFieldValue> values = customFieldValueRepository.findByCustomFieldIdAndIsDeletedFalse(fieldId);
            if (!values.isEmpty()) {
                throw new BusinessRuleException("Required custom field is in use and cannot be deleted.");
            }
        }

        // Soft delete the custom field
        field.markDeleted(actorId.toString());
        customFieldRepository.save(field);

        // Soft delete associated values
        customFieldValueRepository.deleteByCustomFieldId(fieldId, actorId.toString());

        eventPublisher.publishEvent(new CustomFieldDeletedEvent(fieldId, field.getProject().getId(), actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomFieldResponse> getProjectFields(Long projectId, Long actorId) {
        log.info("Retrieving custom fields for project: {} by actor: {}", projectId, actorId);
        checkViewAccess(projectId, actorId);

        List<CustomField> fields = customFieldRepository.findByProjectIdAndIsDeletedFalse(projectId);
        return customFieldMapper.toResponseList(fields);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomFieldResponse> searchFields(Long projectId, String query, Long actorId) {
        log.info("Searching custom fields for project: {} query: {} by actor: {}", projectId, query, actorId);
        checkViewAccess(projectId, actorId);

        List<CustomField> fields = customFieldRepository.searchFields(projectId, query);
        return customFieldMapper.toResponseList(fields);
    }

    private void checkManageAccess(Project project, Long actorId) {
        if (project == null || actorId == null) {
            throw new ForbiddenException("Access Denied: Authentication required.");
        }
        
        // 1. Project Owner always has all permissions
        if (actorId.equals(project.getOwnerId())) {
            return;
        }

        // 2. Workspace Owner / Workspace Admin (PROJECT_MANAGE permission)
        if (workspacePermissionService.hasPermission(project.getWorkspaceId(), actorId, WorkspacePermissionService.PROJECT_MANAGE)) {
            return;
        }

        // 3. Project Member Role check for ADMIN or MANAGER
        WorkspaceMember wsMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserIdAndIsDeletedFalse(project.getWorkspaceId(), actorId)
                .orElse(null);
        if (wsMember != null && wsMember.getStatus() == WorkspaceMemberStatus.ACTIVE) {
            ProjectMember projectMember = projectMemberRepository
                    .findByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(project.getId(), wsMember.getId())
                    .orElse(null);
            if (projectMember != null && projectMember.getStatus() == ProjectMemberStatus.ACTIVE && projectMember.getRoleId() != null) {
                ProjectRole role = projectRoleRepository.findById(projectMember.getRoleId()).orElse(null);
                if (role != null && !role.isDeleted()) {
                    String roleName = role.getName().toUpperCase();
                    if ("ADMIN".equals(roleName) || "MANAGER".equals(roleName) || "OWNER".equals(roleName)) {
                        return;
                    }
                }
            }
        }

        throw new ForbiddenException("Access Denied: Only project admins/managers can manage custom field definitions.");
    }

    private void checkViewAccess(Long projectId, Long actorId) {
        if (!projectPermissionService.canViewProject(projectId, actorId)) {
            throw new ForbiddenException("Access Denied: You do not have permission to view this project.");
        }
    }
}
