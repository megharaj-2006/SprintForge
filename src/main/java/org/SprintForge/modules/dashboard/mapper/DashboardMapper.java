package org.SprintForge.modules.dashboard.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.dashboard.entity.DashboardMetrics;
import org.SprintForge.modules.dashboard.dto.DashboardMetricsResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface DashboardMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    DashboardMetrics toEntity(DashboardMetricsResponse dto);

    DashboardMetricsResponse toDto(DashboardMetrics entity);

    List<DashboardMetricsResponse> toDtoList(List<DashboardMetrics> entities);
}
