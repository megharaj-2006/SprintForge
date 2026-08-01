package org.SprintForge.modules.workspace.project.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.mapstruct.*;
import org.SprintForge.modules.workspace.project.dto.request.MilestoneCreateRequest;
import org.SprintForge.modules.workspace.project.dto.request.MilestoneUpdateRequest;
import org.SprintForge.modules.workspace.project.dto.response.MilestoneResponse;
import org.SprintForge.modules.workspace.project.entity.Milestone;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface MilestoneMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "isArchived", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Milestone toEntity(MilestoneCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "isArchived", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(MilestoneUpdateRequest dto, @MappingTarget Milestone entity);

    MilestoneResponse toResponse(Milestone entity);

    List<MilestoneResponse> toResponseList(List<Milestone> entities);
}
