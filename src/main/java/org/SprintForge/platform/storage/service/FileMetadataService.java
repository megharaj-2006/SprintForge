package org.SprintForge.platform.storage.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.storage.domain.PlatformFileMetadata;
import org.SprintForge.platform.storage.domain.PlatformStoredFile;
import org.SprintForge.platform.storage.dto.FileMetadataResponse;
import org.SprintForge.platform.storage.repository.PlatformFileMetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("platformFileMetadataService")
@RequiredArgsConstructor
public class FileMetadataService {

    private final PlatformFileMetadataRepository metadataRepository;

    @Transactional
    public PlatformFileMetadata setMetadata(PlatformStoredFile storedFile, String key, String value) {
        Optional<PlatformFileMetadata> existing = metadataRepository.findByStoredFileIdAndMetaKey(storedFile.getId(), key);
        PlatformFileMetadata metadata = existing.orElseGet(() -> PlatformFileMetadata.builder()
                .storedFile(storedFile)
                .metaKey(key)
                .build());
        metadata.setMetaValue(value);
        return metadataRepository.save(metadata);
    }

    public List<FileMetadataResponse> getFileMetadata(Long fileId) {
        return metadataRepository.findByStoredFileId(fileId).stream()
                .map(m -> FileMetadataResponse.builder()
                        .id(m.getId())
                        .fileId(m.getStoredFile().getId())
                        .metaKey(m.getMetaKey())
                        .metaValue(m.getMetaValue())
                        .build())
                .collect(Collectors.toList());
    }
}
