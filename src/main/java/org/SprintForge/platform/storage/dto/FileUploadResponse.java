package org.SprintForge.platform.storage.dto;

import org.SprintForge.platform.storage.domain.FileVisibility;
import org.SprintForge.platform.storage.domain.StorageProviderType;

import java.time.Instant;

public class FileUploadResponse {
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
    private String downloadUrl;
    private Instant uploadedAt;

    public FileUploadResponse() {}

    public FileUploadResponse(Long id, String workspaceId, String projectId, String ownerId, String originalName, String mimeType, String extension, long sizeBytes, String checksum, StorageProviderType storageProvider, FileVisibility visibility, String downloadUrl, Instant uploadedAt) {
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
        this.downloadUrl = downloadUrl;
        this.uploadedAt = uploadedAt;
    }

    public static FileUploadResponseBuilder builder() {
        return new FileUploadResponseBuilder();
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

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }

    public static class FileUploadResponseBuilder {
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
        private String downloadUrl;
        private Instant uploadedAt;

        public FileUploadResponseBuilder id(Long id) { this.id = id; return this; }
        public FileUploadResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public FileUploadResponseBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public FileUploadResponseBuilder ownerId(String ownerId) { this.ownerId = ownerId; return this; }
        public FileUploadResponseBuilder originalName(String originalName) { this.originalName = originalName; return this; }
        public FileUploadResponseBuilder mimeType(String mimeType) { this.mimeType = mimeType; return this; }
        public FileUploadResponseBuilder extension(String extension) { this.extension = extension; return this; }
        public FileUploadResponseBuilder sizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; return this; }
        public FileUploadResponseBuilder checksum(String checksum) { this.checksum = checksum; return this; }
        public FileUploadResponseBuilder storageProvider(StorageProviderType storageProvider) { this.storageProvider = storageProvider; return this; }
        public FileUploadResponseBuilder visibility(FileVisibility visibility) { this.visibility = visibility; return this; }
        public FileUploadResponseBuilder downloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; return this; }
        public FileUploadResponseBuilder uploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; return this; }

        public FileUploadResponse build() {
            return new FileUploadResponse(id, workspaceId, projectId, ownerId, originalName, mimeType, extension, sizeBytes, checksum, storageProvider, visibility, downloadUrl, uploadedAt);
        }
    }
}
