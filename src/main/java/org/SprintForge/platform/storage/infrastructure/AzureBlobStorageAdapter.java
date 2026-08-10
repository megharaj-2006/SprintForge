package org.SprintForge.platform.storage.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.storage.domain.StorageProviderType;
import org.SprintForge.platform.storage.ports.StorageProvider;
import org.springframework.stereotype.Component;

@Component("azureBlobStorageAdapter")
public class AzureBlobStorageAdapter implements StorageProvider {

    private static final Logger log = LoggerFactory.getLogger(AzureBlobStorageAdapter.class);

    @Override
    public StorageProviderType getProviderType() {
        return StorageProviderType.AZURE;
    }

    @Override
    public String store(String relativePath, byte[] content) {
        log.info("[Azure Adapter Stub] Storing file at azure://sprintforge-container/{}", relativePath);
        return "azure://sprintforge-container/" + relativePath;
    }

    @Override
    public byte[] load(String relativePath) {
        log.info("[Azure Adapter Stub] Loading file from azure://sprintforge-container/{}", relativePath);
        return new byte[0];
    }

    @Override
    public void delete(String relativePath) {
        log.info("[Azure Adapter Stub] Deleting file from azure://sprintforge-container/{}", relativePath);
    }

    @Override
    public boolean exists(String relativePath) {
        return true;
    }

    @Override
    public String generateDownloadUrl(String relativePath, long expirationSeconds) {
        return "https://sprintforge.blob.core.windows.net/container/" + relativePath + "?sas=" + expirationSeconds;
    }
}
