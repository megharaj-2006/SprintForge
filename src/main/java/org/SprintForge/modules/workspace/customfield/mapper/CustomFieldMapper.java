package org.SprintForge.modules.workspace.customfield.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.SprintForge.modules.workspace.customfield.dto.request.CreateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.request.UpdateCustomFieldRequest;
import org.SprintForge.modules.workspace.customfield.dto.response.CustomFieldResponse;
import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface CustomFieldMapper {

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "archived", ignore = true)
    CustomField toEntity(CreateCustomFieldRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "fieldType", ignore = true)
    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    void updateEntity(UpdateCustomFieldRequest dto, @MappingTarget CustomField entity);

    @Mapping(target = "projectId", source = "project.id")
    CustomFieldResponse toResponse(CustomField entity);

    List<CustomFieldResponse> toResponseList(List<CustomField> entities);
}
