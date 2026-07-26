package org.SprintForge.modules.workspace.form.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.form.entity.Form;
import org.SprintForge.modules.workspace.form.dto.request.FormCreateRequest;
import org.SprintForge.modules.workspace.form.dto.request.FormUpdateRequest;
import org.SprintForge.modules.workspace.form.dto.response.FormResponse;
import org.SprintForge.modules.workspace.form.dto.response.FormSummaryResponse;
import org.SprintForge.modules.workspace.form.dto.response.FormDetailResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Form toEntity(FormCreateRequest dto);

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
    void updateEntity(FormUpdateRequest dto, @MappingTarget Form entity);

    FormResponse toResponse(Form entity);

    @Mapping(target = "submissionCount", ignore = true)
    FormSummaryResponse toSummaryResponse(Form entity);

    @Mapping(target = "createdByUserName", ignore = true)
    @Mapping(target = "fieldCount", ignore = true)
    @Mapping(target = "submissionCount", ignore = true)
    FormDetailResponse toDetailResponse(Form entity);

    List<FormResponse> toResponseList(List<Form> entities);
}
