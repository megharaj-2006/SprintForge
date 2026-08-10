package org.SprintForge.platform.storage.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformFileVersion")
@Table(name = "platform_file_versions")
public class PlatformFileVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stored_file_id", nullable = false)
    private PlatformStoredFile storedFile;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "uploaded_by", nullable = false)
    private String uploadedBy;

    @Column(name = "checksum", nullable = false)
    private String checksum;

    @Column(name = "size_bytes", nullable = false)
    private long sizeBytes;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt = Instant.now();

    public PlatformFileVersion() {}

    public PlatformFileVersion(Long id, PlatformStoredFile storedFile, int versionNumber, String uploadedBy, String checksum, long sizeBytes, String storagePath, Instant uploadedAt) {
        this.id = id;
        this.storedFile = storedFile;
        this.versionNumber = versionNumber;
        this.uploadedBy = uploadedBy;
        this.checksum = checksum;
        this.sizeBytes = sizeBytes;
        this.storagePath = storagePath;
        this.uploadedAt = uploadedAt != null ? uploadedAt : Instant.now();
    }

    public static PlatformFileVersionBuilder builder() {
        return new PlatformFileVersionBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PlatformStoredFile getStoredFile() { return storedFile; }
    public void setStoredFile(PlatformStoredFile storedFile) { this.storedFile = storedFile; }

    public int getVersionNumber() { return versionNumber; }
    public void setVersionNumber(int versionNumber) { this.versionNumber = versionNumber; }

    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }

    public String getChecksum() { return checksum; }
    public void setChecksum(String checksum) { this.checksum = checksum; }

    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }

    public String getStoragePath() { return storagePath; }
    public void setStoragePath(String storagePath) { this.storagePath = storagePath; }

    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }

    public static class PlatformFileVersionBuilder {
        private Long id;
        private PlatformStoredFile storedFile;
        private int versionNumber;
        private String uploadedBy;
        private String checksum;
        private long sizeBytes;
        private String storagePath;
        private Instant uploadedAt = Instant.now();

        public PlatformFileVersionBuilder id(Long id) { this.id = id; return this; }
        public PlatformFileVersionBuilder storedFile(PlatformStoredFile storedFile) { this.storedFile = storedFile; return this; }
        public PlatformFileVersionBuilder versionNumber(int versionNumber) { this.versionNumber = versionNumber; return this; }
        public PlatformFileVersionBuilder uploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; return this; }
        public PlatformFileVersionBuilder checksum(String checksum) { this.checksum = checksum; return this; }
        public PlatformFileVersionBuilder sizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; return this; }
        public PlatformFileVersionBuilder storagePath(String storagePath) { this.storagePath = storagePath; return this; }
        public PlatformFileVersionBuilder uploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; return this; }

        public PlatformFileVersion build() {
            return new PlatformFileVersion(id, storedFile, versionNumber, uploadedBy, checksum, sizeBytes, storagePath, uploadedAt);
        }
    }
}
