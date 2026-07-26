package org.SprintForge.modules.dashboard.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.dashboard.entity.DashboardMetrics;
import org.SprintForge.modules.dashboard.dto.DashboardMetricsDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DashboardMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    DashboardMetrics toEntity(DashboardMetricsDto dto);

    DashboardMetricsDto toDto(DashboardMetrics entity);

    List<DashboardMetricsDto> toDtoList(List<DashboardMetrics> entities);
}
