package org.SprintForge.platform.storage.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.storage.domain.PlatformFileVersion;
import org.SprintForge.platform.storage.domain.PlatformStoredFile;
import org.SprintForge.platform.storage.dto.FileVersionResponse;
import org.SprintForge.platform.storage.repository.PlatformFileVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("platformFileVersionService")
@RequiredArgsConstructor
public class FileVersionService {

    private final PlatformFileVersionRepository versionRepository;

    @Transactional
    public PlatformFileVersion createVersion(PlatformStoredFile storedFile, int versionNumber, String uploadedBy, String checksum, long sizeBytes, String storagePath) {
        PlatformFileVersion version = PlatformFileVersion.builder()
                .storedFile(storedFile)
                .versionNumber(versionNumber)
                .uploadedBy(uploadedBy)
                .checksum(checksum)
                .sizeBytes(sizeBytes)
                .storagePath(storagePath)
                .build();

        return versionRepository.save(version);
    }

    public List<FileVersionResponse> getFileVersions(Long fileId) {
        return versionRepository.findByStoredFileIdOrderByVersionNumberDesc(fileId).stream()
                .map(this::toFileVersionResponse)
                .collect(Collectors.toList());
    }

    public PlatformFileVersion getVersion(Long fileId, int versionNumber) {
        return versionRepository.findByStoredFileIdAndVersionNumber(fileId, versionNumber)
                .orElseThrow(() -> new ResourceNotFoundException("File version not found: " + versionNumber));
    }

    private FileVersionResponse toFileVersionResponse(PlatformFileVersion version) {
        return FileVersionResponse.builder()
                .id(version.getId())
                .fileId(version.getStoredFile().getId())
                .versionNumber(version.getVersionNumber())
                .uploadedBy(version.getUploadedBy())
                .checksum(version.getChecksum())
                .sizeBytes(version.getSizeBytes())
                .uploadedAt(version.getUploadedAt())
                .build();
    }
}
