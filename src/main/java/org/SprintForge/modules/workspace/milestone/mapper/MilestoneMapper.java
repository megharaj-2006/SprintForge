package org.SprintForge.modules.workspace.milestone.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.milestone.entity.Milestone;
import org.SprintForge.modules.workspace.milestone.dto.request.MilestoneCreateRequest;
import org.SprintForge.modules.workspace.milestone.dto.request.MilestoneUpdateRequest;
import org.SprintForge.modules.workspace.milestone.dto.response.MilestoneResponse;
import org.SprintForge.modules.workspace.milestone.dto.response.MilestoneSummaryResponse;
import org.SprintForge.modules.workspace.milestone.dto.response.MilestoneDetailResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    Milestone toEntity(MilestoneCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "createdByUserId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(MilestoneUpdateRequest dto, @MappingTarget Milestone entity);

    MilestoneResponse toResponse(Milestone entity);

    MilestoneSummaryResponse toSummaryResponse(Milestone entity);

    @Mapping(target = "projectName", ignore = true)
    @Mapping(target = "createdByUserName", ignore = true)
    @Mapping(target = "totalTaskCount", ignore = true)
    @Mapping(target = "completedTaskCount", ignore = true)
    MilestoneDetailResponse toDetailResponse(Milestone entity);

    List<MilestoneResponse> toResponseList(List<Milestone> entities);
}
