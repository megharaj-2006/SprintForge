package org.SprintForge.modules.workspace.workspace.branding.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.SprintForge.modules.workspace.workspace.branding.dto.request.WorkspaceBrandingUpdateRequest;
import org.SprintForge.modules.workspace.workspace.branding.dto.response.WorkspaceBrandingResponse;
import org.SprintForge.modules.workspace.workspace.branding.entity.WorkspaceBranding;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = GlobalMapperConfig.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkspaceBrandingMapper {
    WorkspaceBranding toEntity(WorkspaceBrandingUpdateRequest request);
    WorkspaceBrandingResponse toResponse(WorkspaceBranding entity);
    void updateEntity(WorkspaceBrandingUpdateRequest request, @MappingTarget WorkspaceBranding entity);
}
