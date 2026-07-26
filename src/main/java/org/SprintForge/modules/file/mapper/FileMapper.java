package org.SprintForge.modules.file.mapper;

import org.mapstruct.*;
import org.SprintForge.modules.file.entity.FileMetadata;
import org.SprintForge.modules.file.dto.FileDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {
    // TODO: Populate entity and DTO fields first before defining detailed mappings.

    FileMetadata toEntity(FileDto dto);

    FileDto toDto(FileMetadata entity);

    List<FileDto> toDtoList(List<FileMetadata> entities);
}
