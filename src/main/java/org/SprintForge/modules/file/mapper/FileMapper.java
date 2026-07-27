package org.SprintForge.modules.file.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;

import org.mapstruct.*;
import org.SprintForge.modules.file.entity.FileMetadata;
import org.SprintForge.modules.file.dto.FileResponse;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface FileMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    FileMetadata toEntity(FileResponse dto);

    FileResponse toDto(FileMetadata entity);

    List<FileResponse> toDtoList(List<FileMetadata> entities);
}
