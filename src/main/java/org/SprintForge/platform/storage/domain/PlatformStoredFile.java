package org.SprintForge.platform.storage.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformStoredFile")
@Table(name = "platform_stored_files")
public class PlatformStoredFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "stored_name", nullable = false)
    private String storedName;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size_bytes", nullable = false)
    private long sizeBytes;

    @Column(name = "checksum", nullable = false)
    private String checksum;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_provider", nullable = false)
    private StorageProviderType storageProvider;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private FileVisibility visibility;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt = Instant.now();

    public PlatformStoredFile() {}

    public PlatformStoredFile(Long id, String workspaceId, String projectId, String ownerId, String originalName, String storedName, String mimeType, String extension, long sizeBytes, String checksum, StorageProviderType storageProvider, String storagePath, FileVisibility visibility, boolean isDeleted, Instant deletedAt, Instant uploadedAt) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.ownerId = ownerId;
        this.originalName = originalName;
        this.storedName = storedName;
        this.mimeType = mimeType;
        this.extension = extension;
        this.sizeBytes = sizeBytes;
        this.checksum = checksum;
        this.storageProvider = storageProvider;
        this.storagePath = storagePath;
        this.visibility = visibility;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.uploadedAt = uploadedAt != null ? uploadedAt : Instant.now();
    }

    public static PlatformStoredFileBuilder builder() {
        return new PlatformStoredFileBuilder();
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

    public String getStoredName() { return storedName; }
    public void setStoredName(String storedName) { this.storedName = storedName; }

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

    public String getStoragePath() { return storagePath; }
    public void setStoragePath(String storagePath) { this.storagePath = storagePath; }

    public FileVisibility getVisibility() { return visibility; }
    public void setVisibility(FileVisibility visibility) { this.visibility = visibility; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public Instant getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Instant deletedAt) { this.deletedAt = deletedAt; }

    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }

    public static class PlatformStoredFileBuilder {
        private Long id;
        private String workspaceId;
        private String projectId;
        private String ownerId;
        private String originalName;
        private String storedName;
        private String mimeType;
        private String extension;
        private long sizeBytes;
        private String checksum;
        private StorageProviderType storageProvider;
        private String storagePath;
        private FileVisibility visibility;
        private boolean isDeleted;
        private Instant deletedAt;
        private Instant uploadedAt = Instant.now();

        public PlatformStoredFileBuilder id(Long id) { this.id = id; return this; }
        public PlatformStoredFileBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public PlatformStoredFileBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public PlatformStoredFileBuilder ownerId(String ownerId) { this.ownerId = ownerId; return this; }
        public PlatformStoredFileBuilder originalName(String originalName) { this.originalName = originalName; return this; }
        public PlatformStoredFileBuilder storedName(String storedName) { this.storedName = storedName; return this; }
        public PlatformStoredFileBuilder mimeType(String mimeType) { this.mimeType = mimeType; return this; }
        public PlatformStoredFileBuilder extension(String extension) { this.extension = extension; return this; }
        public PlatformStoredFileBuilder sizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; return this; }
        public PlatformStoredFileBuilder checksum(String checksum) { this.checksum = checksum; return this; }
        public PlatformStoredFileBuilder storageProvider(StorageProviderType storageProvider) { this.storageProvider = storageProvider; return this; }
        public PlatformStoredFileBuilder storagePath(String storagePath) { this.storagePath = storagePath; return this; }
        public PlatformStoredFileBuilder visibility(FileVisibility visibility) { this.visibility = visibility; return this; }
        public PlatformStoredFileBuilder isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; return this; }
        public PlatformStoredFileBuilder deletedAt(Instant deletedAt) { this.deletedAt = deletedAt; return this; }
        public PlatformStoredFileBuilder uploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; return this; }

        public PlatformStoredFile build() {
            return new PlatformStoredFile(id, workspaceId, projectId, ownerId, originalName, storedName, mimeType, extension, sizeBytes, checksum, storageProvider, storagePath, visibility, isDeleted, deletedAt, uploadedAt);
        }
    }
}
