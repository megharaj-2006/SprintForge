package org.SprintForge.modules.workspace.customfield.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.SprintForge.modules.workspace.customfield.dto.request.CustomFieldCreateRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.CustomFieldUpdateRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.CustomFieldResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomFieldMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    CustomField toEntity(CustomFieldCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "createdByUserId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(CustomFieldUpdateRequest dto, @MappingTarget CustomField entity);

    CustomFieldResponse toResponse(CustomField entity);

    List<CustomFieldResponse> toResponseList(List<CustomField> entities);
}
