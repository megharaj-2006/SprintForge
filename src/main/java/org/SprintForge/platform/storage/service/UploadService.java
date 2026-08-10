package org.SprintForge.platform.storage.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.storage.domain.*;
import org.SprintForge.platform.storage.dto.FileUploadRequest;
import org.SprintForge.platform.storage.dto.FileUploadResponse;
import org.SprintForge.platform.storage.ports.StorageProvider;
import org.SprintForge.platform.storage.repository.PlatformStoredFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("platformUploadService")
@RequiredArgsConstructor
public class UploadService {

    private static final Logger log = LoggerFactory.getLogger(UploadService.class);
    private final PlatformStoredFileRepository storedFileRepository;
    private final List<StorageProvider> storageProviders;
    private final QuotaService quotaService;
    private final StorageSecurityService securityService;
    private final FileVersionService versionService;
    private final FileMetadataService metadataService;

    @Transactional
    public FileUploadResponse uploadFile(FileUploadRequest request) {
        securityService.validateFileUpload(request.getMimeType(), request.getContent().length);
        if (request.getWorkspaceId() != null) {
            quotaService.validateQuota(request.getWorkspaceId(), request.getContent().length);
        }

        String checksum = securityService.calculateChecksum(request.getContent());
        StorageProvider provider = getStorageProvider(request.getProvider());

        String extension = getFileExtension(request.getOriginalName());
        String storedName = UUID.randomUUID().toString() + (extension.isEmpty() ? "" : "." + extension);
        String relativePath = (request.getWorkspaceId() != null ? request.getWorkspaceId() + "/" : "global/") + storedName;

        String storagePath = provider.store(relativePath, request.getContent());

        PlatformStoredFile storedFile = PlatformStoredFile.builder()
                .workspaceId(request.getWorkspaceId())
                .projectId(request.getProjectId())
                .ownerId(request.getOwnerId())
                .originalName(request.getOriginalName())
                .storedName(storedName)
                .mimeType(request.getMimeType())
                .extension(extension)
                .sizeBytes(request.getContent().length)
                .checksum(checksum)
                .storageProvider(provider.getProviderType())
                .storagePath(storagePath)
                .visibility(request.getVisibility())
                .isDeleted(false)
                .build();

        PlatformStoredFile savedFile = storedFileRepository.save(storedFile);

        versionService.createVersion(savedFile, 1, request.getOwnerId(), checksum, request.getContent().length, storagePath);

        metadataService.setMetadata(savedFile, "file_extension", extension);
        metadataService.setMetadata(savedFile, "original_name", request.getOriginalName());

        String downloadUrl = provider.generateDownloadUrl(relativePath, 3600);

        log.info("Successfully uploaded file {} (id={}) to provider {}", request.getOriginalName(), savedFile.getId(), provider.getProviderType());

        return FileUploadResponse.builder()
                .id(savedFile.getId())
                .workspaceId(savedFile.getWorkspaceId())
                .projectId(savedFile.getProjectId())
                .ownerId(savedFile.getOwnerId())
                .originalName(savedFile.getOriginalName())
                .mimeType(savedFile.getMimeType())
                .extension(savedFile.getExtension())
                .sizeBytes(savedFile.getSizeBytes())
                .checksum(savedFile.getChecksum())
                .storageProvider(savedFile.getStorageProvider())
                .visibility(savedFile.getVisibility())
                .downloadUrl(downloadUrl)
                .uploadedAt(savedFile.getUploadedAt())
                .build();
    }

    private StorageProvider getStorageProvider(StorageProviderType type) {
        return storageProviders.stream()
                .filter(p -> p.getProviderType() == type)
                .findFirst()
                .orElseGet(() -> storageProviders.stream()
                        .filter(p -> p.getProviderType() == StorageProviderType.LOCAL)
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No StorageProvider available")));
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}
