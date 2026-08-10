package org.SprintForge.platform.storage.dto;

import org.SprintForge.platform.storage.domain.FileVisibility;
import org.SprintForge.platform.storage.domain.StorageProviderType;

public class FileUploadRequest {
    private String workspaceId;
    private String projectId;
    private String ownerId;
    private String originalName;
    private String mimeType;
    private byte[] content;
    private StorageProviderType provider = StorageProviderType.LOCAL;
    private FileVisibility visibility = FileVisibility.PRIVATE;

    public FileUploadRequest() {}

    public FileUploadRequest(String workspaceId, String projectId, String ownerId, String originalName, String mimeType, byte[] content, StorageProviderType provider, FileVisibility visibility) {
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.ownerId = ownerId;
        this.originalName = originalName;
        this.mimeType = mimeType;
        this.content = content;
        this.provider = provider != null ? provider : StorageProviderType.LOCAL;
        this.visibility = visibility != null ? visibility : FileVisibility.PRIVATE;
    }

    public static FileUploadRequestBuilder builder() {
        return new FileUploadRequestBuilder();
    }

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

    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }

    public StorageProviderType getProvider() { return provider; }
    public void setProvider(StorageProviderType provider) { this.provider = provider; }

    public FileVisibility getVisibility() { return visibility; }
    public void setVisibility(FileVisibility visibility) { this.visibility = visibility; }

    public static class FileUploadRequestBuilder {
        private String workspaceId;
        private String projectId;
        private String ownerId;
        private String originalName;
        private String mimeType;
        private byte[] content;
        private StorageProviderType provider = StorageProviderType.LOCAL;
        private FileVisibility visibility = FileVisibility.PRIVATE;

        public FileUploadRequestBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public FileUploadRequestBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public FileUploadRequestBuilder ownerId(String ownerId) { this.ownerId = ownerId; return this; }
        public FileUploadRequestBuilder originalName(String originalName) { this.originalName = originalName; return this; }
        public FileUploadRequestBuilder mimeType(String mimeType) { this.mimeType = mimeType; return this; }
        public FileUploadRequestBuilder content(byte[] content) { this.content = content; return this; }
        public FileUploadRequestBuilder provider(StorageProviderType provider) { this.provider = provider; return this; }
        public FileUploadRequestBuilder visibility(FileVisibility visibility) { this.visibility = visibility; return this; }

        public FileUploadRequest build() {
            return new FileUploadRequest(workspaceId, projectId, ownerId, originalName, mimeType, content, provider, visibility);
        }
    }
}
