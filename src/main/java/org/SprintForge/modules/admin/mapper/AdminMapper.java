package org.SprintForge.modules.admin.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.admin.entity.SystemConfiguration;
import org.SprintForge.modules.admin.dto.SystemConfigurationResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface AdminMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    SystemConfiguration toEntity(SystemConfigurationResponse dto);

    SystemConfigurationResponse toDto(SystemConfiguration entity);

    List<SystemConfigurationResponse> toDtoList(List<SystemConfiguration> entities);
}
