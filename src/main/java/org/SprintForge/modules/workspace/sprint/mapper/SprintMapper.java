package org.SprintForge.modules.workspace.sprint.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintCreateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintUpdateRequest;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintDetailResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintSummaryResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SprintMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "completedStoryPoints", ignore = true)
    @Mapping(target = "velocity", ignore = true)
    @Mapping(target = "completedTaskCount", ignore = true)
    @Mapping(target = "totalTaskCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Sprint toEntity(SprintCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "velocity", ignore = true)
    @Mapping(target = "completedTaskCount", ignore = true)
    @Mapping(target = "totalTaskCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(SprintUpdateRequest dto, @MappingTarget Sprint entity);

    SprintResponse toResponse(Sprint entity);

    SprintSummaryResponse toSummaryResponse(Sprint entity);

    @Mapping(target = "projectName", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    SprintDetailResponse toDetailResponse(Sprint entity);

    List<SprintResponse> toResponseList(List<Sprint> entities);
}
