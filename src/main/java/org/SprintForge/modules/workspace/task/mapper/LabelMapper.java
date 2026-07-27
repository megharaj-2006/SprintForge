package org.SprintForge.modules.workspace.task.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.task.dto.request.CreateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.request.UpdateLabelRequest;
import org.SprintForge.modules.workspace.task.dto.response.LabelResponse;
import org.SprintForge.modules.workspace.task.dto.response.LabelSummaryResponse;
import org.SprintForge.modules.workspace.task.entity.Label;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LabelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "version", ignore = true)
    Label toEntity(CreateLabelRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(UpdateLabelRequest dto, @MappingTarget Label entity);

    @Mapping(target = "projectId", source = "project.id")
    LabelResponse toResponse(Label entity);

    LabelSummaryResponse toSummaryResponse(Label entity);

    List<LabelResponse> toResponseList(List<Label> entities);

    List<LabelSummaryResponse> toSummaryResponseList(List<Label> entities);
}