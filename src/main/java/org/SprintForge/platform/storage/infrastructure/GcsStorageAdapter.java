package org.SprintForge.platform.storage.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.storage.domain.StorageProviderType;
import org.SprintForge.platform.storage.ports.StorageProvider;
import org.springframework.stereotype.Component;

@Component("gcsStorageAdapter")
public class GcsStorageAdapter implements StorageProvider {

    private static final Logger log = LoggerFactory.getLogger(GcsStorageAdapter.class);

    @Override
    public StorageProviderType getProviderType() {
        return StorageProviderType.GCS;
    }

    @Override
    public String store(String relativePath, byte[] content) {
        log.info("[GCS Adapter Stub] Storing file at gs://sprintforge-bucket/{}", relativePath);
        return "gs://sprintforge-bucket/" + relativePath;
    }

    @Override
    public byte[] load(String relativePath) {
        log.info("[GCS Adapter Stub] Loading file from gs://sprintforge-bucket/{}", relativePath);
        return new byte[0];
    }

    @Override
    public void delete(String relativePath) {
        log.info("[GCS Adapter Stub] Deleting file from gs://sprintforge-bucket/{}", relativePath);
    }

    @Override
    public boolean exists(String relativePath) {
        return true;
    }

    @Override
    public String generateDownloadUrl(String relativePath, long expirationSeconds) {
        return "https://storage.googleapis.com/sprintforge-bucket/" + relativePath + "?expires=" + expirationSeconds;
    }
}
