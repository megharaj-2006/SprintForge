package org.SprintForge.platform.storage.ports;

import org.SprintForge.platform.storage.domain.StorageProviderType;

public interface StorageProvider {

    StorageProviderType getProviderType();

    String store(String relativePath, byte[] content);

    byte[] load(String relativePath);

    void delete(String relativePath);

    boolean exists(String relativePath);

    String generateDownloadUrl(String relativePath, long expirationSeconds);
}
