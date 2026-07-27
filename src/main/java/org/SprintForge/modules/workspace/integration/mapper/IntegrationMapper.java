package org.SprintForge.modules.workspace.integration.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.integration.entity.Integration;
import org.SprintForge.modules.workspace.integration.dto.request.IntegrationCreateRequest;
import org.SprintForge.modules.workspace.integration.dto.response.IntegrationResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface IntegrationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "lastSyncedAt", ignore = true)
    Integration toEntity(IntegrationCreateRequest dto);

    IntegrationResponse toResponse(Integration entity);

    List<IntegrationResponse> toResponseList(List<Integration> entities);
}
