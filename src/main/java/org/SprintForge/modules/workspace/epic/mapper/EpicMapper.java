package org.SprintForge.modules.workspace.epic.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.epic.entity.Epic;
import org.SprintForge.modules.workspace.epic.dto.request.EpicCreateRequest;
import org.SprintForge.modules.workspace.epic.dto.request.EpicUpdateRequest;
import org.SprintForge.modules.workspace.epic.dto.response.EpicDetailResponse;
import org.SprintForge.modules.workspace.epic.dto.response.EpicResponse;
import org.SprintForge.modules.workspace.epic.dto.response.EpicSummaryResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EpicMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "completedStoryPoints", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Epic toEntity(EpicCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(EpicUpdateRequest dto, @MappingTarget Epic entity);

    EpicResponse toResponse(Epic entity);

    EpicSummaryResponse toSummaryResponse(Epic entity);

    @Mapping(target = "projectName", ignore = true)
    @Mapping(target = "ownerName", ignore = true)
    @Mapping(target = "totalTaskCount", ignore = true)
    @Mapping(target = "completedTaskCount", ignore = true)
    EpicDetailResponse toDetailResponse(Epic entity);

    List<EpicResponse> toResponseList(List<Epic> entities);
}
