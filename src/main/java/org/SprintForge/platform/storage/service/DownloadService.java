package org.SprintForge.platform.storage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.storage.domain.PlatformStoredFile;
import org.SprintForge.platform.storage.domain.StorageProviderType;
import org.SprintForge.platform.storage.ports.StorageProvider;
import org.SprintForge.platform.storage.repository.PlatformStoredFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("platformDownloadService")
@RequiredArgsConstructor
public class DownloadService {

    private final PlatformStoredFileRepository storedFileRepository;
    private final List<StorageProvider> storageProviders;
    private final StorageSecurityService securityService;

    public byte[] downloadFile(Long fileId, String requestingUserId) {
        PlatformStoredFile file = storedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Stored file not found with ID: " + fileId));

        securityService.checkDownloadPermission(file, requestingUserId);

        StorageProvider provider = getStorageProvider(file.getStorageProvider());
        String relativePath = (file.getWorkspaceId() != null ? file.getWorkspaceId() + "/" : "global/") + file.getStoredName();

        return provider.load(relativePath);
    }

    public String generateSignedDownloadUrl(Long fileId, String requestingUserId, long expirationSeconds) {
        PlatformStoredFile file = storedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("Stored file not found with ID: " + fileId));

        securityService.checkDownloadPermission(file, requestingUserId);

        StorageProvider provider = getStorageProvider(file.getStorageProvider());
        String relativePath = (file.getWorkspaceId() != null ? file.getWorkspaceId() + "/" : "global/") + file.getStoredName();

        return provider.generateDownloadUrl(relativePath, expirationSeconds);
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
}
