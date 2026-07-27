package org.SprintForge.modules.workspace.analytics.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.analytics.entity.Dashboard;
import org.SprintForge.modules.workspace.analytics.dto.request.DashboardCreateRequest;
import org.SprintForge.modules.workspace.analytics.dto.request.DashboardUpdateRequest;
import org.SprintForge.modules.workspace.analytics.dto.response.DashboardResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface AnalyticsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    Dashboard toEntity(DashboardCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workspaceId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(DashboardUpdateRequest dto, @MappingTarget Dashboard entity);

    DashboardResponse toResponse(Dashboard entity);

    List<DashboardResponse> toResponseList(List<Dashboard> entities);
}
