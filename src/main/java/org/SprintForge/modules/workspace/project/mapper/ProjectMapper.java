package org.SprintForge.modules.workspace.project.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.project.dto.request.ProjectCreateRequest;
import org.SprintForge.modules.workspace.project.dto.request.ProjectUpdateRequest;
import org.SprintForge.modules.workspace.project.dto.request.ProjectSettingsRequest;
import org.SprintForge.modules.workspace.project.dto.response.*;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.ProjectSettings;
import org.SprintForge.modules.workspace.project.entity.ProjectRole;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "loggedHours", ignore = true)
    @Mapping(target = "isArchived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Project toEntity(ProjectCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "projectKey", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "loggedHours", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(ProjectUpdateRequest dto, @MappingTarget Project entity);

    ProjectResponse toResponse(Project entity);

    ProjectSummaryResponse toSummaryResponse(Project entity);

    @Mapping(target = "ownerName", ignore = true)
    @Mapping(target = "ownerEmail", ignore = true)
    @Mapping(target = "activeMemberCount", ignore = true)
    @Mapping(target = "openTaskCount", ignore = true)
    @Mapping(target = "completedTaskCount", ignore = true)
    ProjectDetailResponse toDetailResponse(Project entity);

    List<ProjectResponse> toResponseList(List<Project> entities);

    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "userEmail", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "roleName", ignore = true)
    ProjectMemberResponse toResponse(ProjectMember entity);

    List<ProjectMemberResponse> toMemberResponseList(List<ProjectMember> entities);

    ProjectSettingsResponse toResponse(ProjectSettings entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(ProjectSettingsRequest dto, @MappingTarget ProjectSettings entity);

    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "roleName", ignore = true)
    ProjectMemberSummaryResponse toSummaryResponse(ProjectMember entity);

    List<ProjectMemberSummaryResponse> toSummaryResponseList(List<ProjectMember> entities);

    ProjectRoleResponse toResponse(ProjectRole entity);

    List<ProjectRoleResponse> toRoleResponseList(List<ProjectRole> entities);
}
