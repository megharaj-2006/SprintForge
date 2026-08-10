package org.SprintForge.platform.storage.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.storage.domain.PlatformStoredFile;
import org.SprintForge.platform.storage.dto.StoredFileResponse;
import org.SprintForge.platform.storage.repository.PlatformStoredFileRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service("platformStorageService")
@RequiredArgsConstructor
public class StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);
    private final PlatformStoredFileRepository storedFileRepository;

    public StoredFileResponse getFileDetails(Long fileId) {
        PlatformStoredFile file = storedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Stored file not found with ID: " + fileId));
        return toStoredFileResponse(file);
    }

    public List<StoredFileResponse> getWorkspaceFiles(String workspaceId, int page, int size) {
        return storedFileRepository.findByWorkspaceIdAndDeletedFalse(workspaceId, PageRequest.of(page, size)).stream()
                .map(this::toStoredFileResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFile(Long fileId) {
        PlatformStoredFile file = storedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Stored file not found with ID: " + fileId));
        file.setDeleted(true);
        file.setDeletedAt(Instant.now());
        storedFileRepository.save(file);
        log.info("Soft-deleted stored file id={}", fileId);
    }

    @Transactional
    public StoredFileResponse restoreFile(Long fileId) {
        PlatformStoredFile file = storedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Stored file not found with ID: " + fileId));
        file.setDeleted(false);
        file.setDeletedAt(null);
        PlatformStoredFile saved = storedFileRepository.save(file);
        log.info("Restored soft-deleted stored file id={}", fileId);
        return toStoredFileResponse(saved);
    }

    private StoredFileResponse toStoredFileResponse(PlatformStoredFile file) {
        return StoredFileResponse.builder()
                .id(file.getId())
                .workspaceId(file.getWorkspaceId())
                .projectId(file.getProjectId())
                .ownerId(file.getOwnerId())
                .originalName(file.getOriginalName())
                .mimeType(file.getMimeType())
                .extension(file.getExtension())
                .sizeBytes(file.getSizeBytes())
                .checksum(file.getChecksum())
                .storageProvider(file.getStorageProvider())
                .visibility(file.getVisibility())
                .isDeleted(file.isDeleted())
                .uploadedAt(file.getUploadedAt())
                .build();
    }
}
