package org.SprintForge.modules.workspace.workspace.team.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.SprintForge.modules.workspace.workspace.team.dto.request.CreateTeamRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.request.UpdateTeamRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamResponse;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamSummaryResponse;
import org.SprintForge.modules.workspace.workspace.team.entity.Team;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = GlobalMapperConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, builder = @org.mapstruct.Builder(disableBuilder = true))
public interface TeamMapper {

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
    @Mapping(target = "parentTeamId", ignore = true)
    @Mapping(target = "createdById", ignore = true)
    Team toEntity(CreateTeamRequest request);

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
    @Mapping(target = "parentTeamId", ignore = true)
    @Mapping(target = "createdById", ignore = true)
    void updateEntity(UpdateTeamRequest request, @MappingTarget Team team);

    @Mapping(target = "memberCount", ignore = true)
    TeamResponse toResponse(Team team);

    @Mapping(target = "memberCount", ignore = true)
    TeamSummaryResponse toSummaryResponse(Team team);
}
