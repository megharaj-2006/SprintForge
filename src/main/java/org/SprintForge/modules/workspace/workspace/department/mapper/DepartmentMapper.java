package org.SprintForge.modules.workspace.workspace.department.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.SprintForge.modules.workspace.workspace.department.dto.request.CreateDepartmentRequest;
import org.SprintForge.modules.workspace.workspace.department.dto.request.UpdateDepartmentRequest;
import org.SprintForge.modules.workspace.workspace.department.dto.response.DepartmentResponse;
import org.SprintForge.modules.workspace.workspace.department.dto.response.DepartmentSummaryResponse;
import org.SprintForge.modules.workspace.workspace.department.entity.Department;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = GlobalMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface DepartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "archived", ignore = true)
    Department toEntity(CreateDepartmentRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "archived", ignore = true)
    void updateEntity(UpdateDepartmentRequest request, @MappingTarget Department department);

    @Mapping(target = "teamCount", ignore = true)
    @Mapping(target = "memberCount", ignore = true)
    DepartmentResponse toResponse(Department department);

    @Mapping(target = "teamCount", ignore = true)
    DepartmentSummaryResponse toSummaryResponse(Department department);
}
