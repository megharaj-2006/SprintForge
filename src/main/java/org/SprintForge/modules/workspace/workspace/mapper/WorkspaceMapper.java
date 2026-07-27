package org.SprintForge.modules.workspace.workspace.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import org.SprintForge.modules.workspace.workspace.entity.*;
import org.SprintForge.modules.workspace.workspace.dto.request.*;
import org.SprintForge.modules.workspace.workspace.dto.response.*;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface WorkspaceMapper {

    // ==========================================
    // Workspace Mappings
    // ==========================================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "inviteCode", ignore = true)
    @Mapping(target = "storageUsed", ignore = true)
    @Mapping(target = "archived", ignore = true)
    Workspace toEntity(WorkspaceCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "inviteCode", ignore = true)
    @Mapping(target = "storageUsed", ignore = true)
    @Mapping(target = "archived", ignore = true)
    void updateEntity(WorkspaceUpdateRequest dto, @MappingTarget Workspace entity);

    @Mapping(target = "isArchived", source = "archived")
    WorkspaceResponse toResponse(Workspace entity);

    @Mapping(target = "isArchived", source = "archived")
    @Mapping(target = "memberCount", ignore = true)
    WorkspaceSummaryResponse toSummaryResponse(Workspace entity);

    @Mapping(target = "isArchived", source = "archived")
    @Mapping(target = "ownerName", ignore = true)
    @Mapping(target = "ownerEmail", ignore = true)
    @Mapping(target = "activeMemberCount", ignore = true)
    @Mapping(target = "projectCount", ignore = true)
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "roles", ignore = true)
    WorkspaceDetailResponse toDetailResponse(Workspace entity);

    List<WorkspaceResponse> toResponseList(List<Workspace> entities);

    List<WorkspaceSummaryResponse> toSummaryResponseList(List<Workspace> entities);

    // ==========================================
    // WorkspaceMember Mappings
    // ==========================================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "joinedAt", ignore = true)
    @Mapping(target = "lastSeenAt", ignore = true)
    @Mapping(target = "isFavoriteWorkspace", ignore = true)
    @Mapping(target = "isStarred", ignore = true)
    @Mapping(target = "notificationPreferenceId", ignore = true)
    WorkspaceMember toEntity(WorkspaceMemberCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "joinedViaInvite", ignore = true)
    @Mapping(target = "joinedAt", ignore = true)
    @Mapping(target = "lastSeenAt", ignore = true)
    void updateEntity(WorkspaceMemberUpdateRequest dto, @MappingTarget WorkspaceMember entity);

    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "userEmail", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "roleName", ignore = true)
    WorkspaceMemberResponse toResponse(WorkspaceMember entity);

    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "userEmail", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "roleName", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "assignedProjectIds", ignore = true)
    WorkspaceMemberDetailResponse toDetailResponse(WorkspaceMember entity);

    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "roleName", ignore = true)
    WorkspaceMemberSummaryResponse toSummaryResponse(WorkspaceMember entity);

    List<WorkspaceMemberResponse> toMemberResponseList(List<WorkspaceMember> entities);

    // ==========================================
    // WorkspaceInvitation Mappings
    // ==========================================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "invitedUserId", ignore = true)
    @Mapping(target = "invitedBy", ignore = true)
    @Mapping(target = "inviteToken", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "acceptedAt", ignore = true)
    @Mapping(target = "rejectedAt", ignore = true)
    WorkspaceInvitation toEntity(WorkspaceInviteRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "invitedUserId", ignore = true)
    @Mapping(target = "invitedBy", ignore = true)
    @Mapping(target = "inviteToken", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "acceptedAt", ignore = true)
    @Mapping(target = "rejectedAt", ignore = true)
    WorkspaceInvitation toEntity(InviteMemberRequest dto);

    @Mapping(target = "token", source = "inviteToken")
    @Mapping(target = "roleName", ignore = true)
    @Mapping(target = "inviterName", ignore = true)
    WorkspaceInvitationResponse toResponse(WorkspaceInvitation entity);

    List<WorkspaceInvitationResponse> toInvitationResponseList(List<WorkspaceInvitation> entities);


    // ==========================================
    // WorkspaceRole Mappings
    // ==========================================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "isSystemRole", ignore = true)
    WorkspaceRole toEntity(WorkspaceRoleCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "isSystemRole", ignore = true)
    void updateEntity(WorkspaceRoleUpdateRequest dto, @MappingTarget WorkspaceRole entity);

    WorkspaceRoleResponse toResponse(WorkspaceRole entity);

    List<WorkspaceRoleResponse> toRoleResponseList(List<WorkspaceRole> entities);

    // ==========================================
    // WorkspaceSettings Mappings
    // ==========================================

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "banner", ignore = true)
    @Mapping(target = "primaryColor", ignore = true)
    @Mapping(target = "secondaryColor", ignore = true)
    @Mapping(target = "theme", ignore = true)
    @Mapping(target = "customDomain", ignore = true)
    @Mapping(target = "favicon", ignore = true)
    void updateEntity(WorkspaceSettingsUpdateRequest dto, @MappingTarget WorkspaceSettings entity);

    WorkspaceSettingsResponse toResponse(WorkspaceSettings entity);

    // ==========================================
    // WorkspaceWebhook Mappings
    // ==========================================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastTriggeredAt", ignore = true)
    WorkspaceWebhook toEntity(WorkspaceWebhookCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "lastTriggeredAt", ignore = true)
    void updateEntity(WorkspaceWebhookUpdateRequest dto, @MappingTarget WorkspaceWebhook entity);

    WorkspaceWebhookResponse toResponse(WorkspaceWebhook entity);

    List<WorkspaceWebhookResponse> toWebhookResponseList(List<WorkspaceWebhook> entities);

    // ==========================================
    // WorkspaceActivity & Audit Log Mappings
    // ==========================================

    @Mapping(target = "userName", ignore = true)
    WorkspaceActivityResponse toResponse(WorkspaceActivity entity);

    List<WorkspaceActivityResponse> toActivityResponseList(List<WorkspaceActivity> entities);

    @Mapping(target = "userId", source = "performedBy")
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "timestamp", source = "createdAt")
    WorkspaceAuditResponse toResponse(WorkspaceAuditLog entity);

    List<WorkspaceAuditResponse> toAuditResponseList(List<WorkspaceAuditLog> entities);

    // ==========================================
    // WorkspacePreference Mappings
    // ==========================================

    WorkspacePreferenceResponse toResponse(WorkspacePreference entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(WorkspacePreferenceRequest dto, @MappingTarget WorkspacePreference entity);
}
