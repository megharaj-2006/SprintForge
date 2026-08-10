package org.SprintForge.platform.storage.infrastructure;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.storage.domain.StorageProviderType;
import org.SprintForge.platform.storage.ports.StorageProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component("localStorageAdapter")
public class LocalStorageAdapter implements StorageProvider {

    private static final Logger log = LoggerFactory.getLogger(LocalStorageAdapter.class);

    @Value("${sprintforge.storage.local.root-dir:./uploads}")
    private String rootDir;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        this.rootLocation = Paths.get(rootDir);
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            log.error("Failed to initialize storage directory at {}", rootLocation, e);
        }
    }

    @Override
    public StorageProviderType getProviderType() {
        return StorageProviderType.LOCAL;
    }

    @Override
    public String store(String relativePath, byte[] content) {
        try {
            Path target = this.rootLocation.resolve(relativePath).normalize();
            Files.createDirectories(target.getParent());
            Files.write(target, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return target.toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("Failed to store file at relative path {}", relativePath, e);
            throw new RuntimeException("Failed to store file: " + e.getMessage());
        }
    }

    @Override
    public byte[] load(String relativePath) {
        try {
            Path target = this.rootLocation.resolve(relativePath).normalize();
            if (!Files.exists(target)) {
                throw new RuntimeException("File not found at path: " + relativePath);
            }
            return Files.readAllBytes(target);
        } catch (IOException e) {
            log.error("Failed to load file at relative path {}", relativePath, e);
            throw new RuntimeException("Failed to load file: " + e.getMessage());
        }
    }

    @Override
    public void delete(String relativePath) {
        try {
            Path target = this.rootLocation.resolve(relativePath).normalize();
            Files.deleteIfExists(target);
        } catch (IOException e) {
            log.error("Failed to delete file at relative path {}", relativePath, e);
        }
    }

    @Override
    public boolean exists(String relativePath) {
        Path target = this.rootLocation.resolve(relativePath).normalize();
        return Files.exists(target);
    }

    @Override
    public String generateDownloadUrl(String relativePath, long expirationSeconds) {
        return "/api/v1/files/download?path=" + relativePath;
    }
}
