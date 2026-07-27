package org.SprintForge.modules.workspace.sprint.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.entity.SprintGoal;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintCreateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintUpdateRequest;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintDetailResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintSummaryResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintGoalResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface SprintMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "completedStoryPoints", ignore = true)
    @Mapping(target = "velocity", ignore = true)
    @Mapping(target = "completedTaskCount", ignore = true)
    @Mapping(target = "totalTaskCount", ignore = true)
    @Mapping(target = "orderIndex", ignore = true)
    @Mapping(target = "archivedAt", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    Sprint toEntity(SprintCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "velocity", ignore = true)
    @Mapping(target = "completedTaskCount", ignore = true)
    @Mapping(target = "totalTaskCount", ignore = true)
    @Mapping(target = "orderIndex", ignore = true)
    @Mapping(target = "archivedAt", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(SprintUpdateRequest request, @MappingTarget Sprint entity);

    SprintResponse toResponse(Sprint entity);

    @Mapping(target = "projectName", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "goals", ignore = true)
    SprintDetailResponse toDetailResponse(Sprint entity);

    SprintSummaryResponse toSummaryResponse(Sprint entity);

    List<SprintResponse> toResponseList(List<Sprint> sprints);

    List<SprintSummaryResponse> toSummaryResponseList(List<Sprint> sprints);

    SprintGoalResponse toGoalResponse(SprintGoal goal);

    List<SprintGoalResponse> toGoalResponseList(List<SprintGoal> goals);
}
