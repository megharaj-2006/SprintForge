package org.SprintForge.modules.admin.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.admin.entity.SystemConfiguration;
import org.SprintForge.modules.admin.dto.SystemConfigurationDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    SystemConfiguration toEntity(SystemConfigurationDto dto);

    SystemConfigurationDto toDto(SystemConfiguration entity);

    List<SystemConfigurationDto> toDtoList(List<SystemConfiguration> entities);
}
