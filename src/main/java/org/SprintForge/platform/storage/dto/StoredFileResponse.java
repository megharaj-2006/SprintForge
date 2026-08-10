package org.SprintForge.platform.storage.dto;

import org.SprintForge.platform.storage.domain.FileVisibility;
import org.SprintForge.platform.storage.domain.StorageProviderType;

import java.time.Instant;

public class StoredFileResponse {
    private Long id;
    private String workspaceId;
    private String projectId;
    private String ownerId;
    private String originalName;
    private String mimeType;
    private String extension;
    private long sizeBytes;
    private String checksum;
    private StorageProviderType storageProvider;
    private FileVisibility visibility;
    private boolean isDeleted;
    private Instant uploadedAt;

    public StoredFileResponse() {}

    public StoredFileResponse(Long id, String workspaceId, String projectId, String ownerId, String originalName, String mimeType, String extension, long sizeBytes, String checksum, StorageProviderType storageProvider, FileVisibility visibility, boolean isDeleted, Instant uploadedAt) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.ownerId = ownerId;
        this.originalName = originalName;
        this.mimeType = mimeType;
        this.extension = extension;
        this.sizeBytes = sizeBytes;
        this.checksum = checksum;
        this.storageProvider = storageProvider;
        this.visibility = visibility;
        this.isDeleted = isDeleted;
        this.uploadedAt = uploadedAt;
    }

    public static StoredFileResponseBuilder builder() {
        return new StoredFileResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getExtension() { return extension; }
    public void setExtension(String extension) { this.extension = extension; }

    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }

    public String getChecksum() { return checksum; }
    public void setChecksum(String checksum) { this.checksum = checksum; }

    public StorageProviderType getStorageProvider() { return storageProvider; }
    public void setStorageProvider(StorageProviderType storageProvider) { this.storageProvider = storageProvider; }

    public FileVisibility getVisibility() { return visibility; }
    public void setVisibility(FileVisibility visibility) { this.visibility = visibility; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }

    public static class StoredFileResponseBuilder {
        private Long id;
        private String workspaceId;
        private String projectId;
        private String ownerId;
        private String originalName;
        private String mimeType;
        private String extension;
        private long sizeBytes;
        private String checksum;
        private StorageProviderType storageProvider;
        private FileVisibility visibility;
        private boolean isDeleted;
        private Instant uploadedAt;

        public StoredFileResponseBuilder id(Long id) { this.id = id; return this; }
        public StoredFileResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public StoredFileResponseBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public StoredFileResponseBuilder ownerId(String ownerId) { this.ownerId = ownerId; return this; }
        public StoredFileResponseBuilder originalName(String originalName) { this.originalName = originalName; return this; }
        public StoredFileResponseBuilder mimeType(String mimeType) { this.mimeType = mimeType; return this; }
        public StoredFileResponseBuilder extension(String extension) { this.extension = extension; return this; }
        public StoredFileResponseBuilder sizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; return this; }
        public StoredFileResponseBuilder checksum(String checksum) { this.checksum = checksum; return this; }
        public StoredFileResponseBuilder storageProvider(StorageProviderType storageProvider) { this.storageProvider = storageProvider; return this; }
        public StoredFileResponseBuilder visibility(FileVisibility visibility) { this.visibility = visibility; return this; }
        public StoredFileResponseBuilder isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; return this; }
        public StoredFileResponseBuilder uploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; return this; }

        public StoredFileResponse build() {
            return new StoredFileResponse(id, workspaceId, projectId, ownerId, originalName, mimeType, extension, sizeBytes, checksum, storageProvider, visibility, isDeleted, uploadedAt);
        }
    }
}
