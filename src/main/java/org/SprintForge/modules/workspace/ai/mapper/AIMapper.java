package org.SprintForge.modules.workspace.ai.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.workspace.ai.entity.AISuggestion;
import org.SprintForge.modules.workspace.ai.dto.request.AISuggestionRequest;
import org.SprintForge.modules.workspace.ai.dto.response.AISuggestionResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface AIMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "accepted", ignore = true)
    @Mapping(target = "acceptedBy", ignore = true)
    @Mapping(target = "acceptedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    AISuggestion toEntity(AISuggestionRequest dto);

    AISuggestionResponse toResponse(AISuggestion entity);

    List<AISuggestionResponse> toResponseList(List<AISuggestion> entities);
}
