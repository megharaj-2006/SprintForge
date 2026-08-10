package org.SprintForge.platform.storage.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.storage.domain.StorageProviderType;
import org.SprintForge.platform.storage.ports.StorageProvider;
import org.springframework.stereotype.Component;

@Component("s3StorageAdapter")
public class S3StorageAdapter implements StorageProvider {

    private static final Logger log = LoggerFactory.getLogger(S3StorageAdapter.class);

    @Override
    public StorageProviderType getProviderType() {
        return StorageProviderType.S3;
    }

    @Override
    public String store(String relativePath, byte[] content) {
        log.info("[S3 Adapter Stub] Storing file at s3://sprintforge-bucket/{}", relativePath);
        return "s3://sprintforge-bucket/" + relativePath;
    }

    @Override
    public byte[] load(String relativePath) {
        log.info("[S3 Adapter Stub] Loading file from s3://sprintforge-bucket/{}", relativePath);
        return new byte[0];
    }

    @Override
    public void delete(String relativePath) {
        log.info("[S3 Adapter Stub] Deleting file from s3://sprintforge-bucket/{}", relativePath);
    }

    @Override
    public boolean exists(String relativePath) {
        return true;
    }

    @Override
    public String generateDownloadUrl(String relativePath, long expirationSeconds) {
        return "https://sprintforge-bucket.s3.amazonaws.com/" + relativePath + "?X-Amz-Expires=" + expirationSeconds;
    }
}
