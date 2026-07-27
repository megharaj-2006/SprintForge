package org.SprintForge.modules.workspace.goal.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.goal.entity.ProjectGoal;
import org.SprintForge.modules.workspace.goal.dto.request.GoalCreateRequest;
import org.SprintForge.modules.workspace.goal.dto.request.GoalUpdateRequest;
import org.SprintForge.modules.workspace.goal.dto.response.GoalResponse;
import org.SprintForge.modules.workspace.goal.dto.response.GoalSummaryResponse;
import org.SprintForge.modules.workspace.goal.dto.response.GoalDetailResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface GoalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    ProjectGoal toEntity(GoalCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(GoalUpdateRequest dto, @MappingTarget ProjectGoal entity);

    GoalResponse toResponse(ProjectGoal entity);

    GoalSummaryResponse toSummaryResponse(ProjectGoal entity);

    @Mapping(target = "projectName", ignore = true)
    @Mapping(target = "ownerName", ignore = true)
    @Mapping(target = "keyResultCount", ignore = true)
    GoalDetailResponse toDetailResponse(ProjectGoal entity);

    List<GoalResponse> toResponseList(List<ProjectGoal> entities);
}
