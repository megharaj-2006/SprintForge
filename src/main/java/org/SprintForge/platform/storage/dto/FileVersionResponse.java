package org.SprintForge.platform.storage.dto;

import java.time.Instant;

public class FileVersionResponse {
    private Long id;
    private Long fileId;
    private int versionNumber;
    private String uploadedBy;
    private String checksum;
    private long sizeBytes;
    private Instant uploadedAt;

    public FileVersionResponse() {}

    public FileVersionResponse(Long id, Long fileId, int versionNumber, String uploadedBy, String checksum, long sizeBytes, Instant uploadedAt) {
        this.id = id;
        this.fileId = fileId;
        this.versionNumber = versionNumber;
        this.uploadedBy = uploadedBy;
        this.checksum = checksum;
        this.sizeBytes = sizeBytes;
        this.uploadedAt = uploadedAt;
    }

    public static FileVersionResponseBuilder builder() {
        return new FileVersionResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFileId() { return fileId; }
    public void setFileId(Long fileId) { this.fileId = fileId; }

    public int getVersionNumber() { return versionNumber; }
    public void setVersionNumber(int versionNumber) { this.versionNumber = versionNumber; }

    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }

    public String getChecksum() { return checksum; }
    public void setChecksum(String checksum) { this.checksum = checksum; }

    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }

    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; }

    public static class FileVersionResponseBuilder {
        private Long id;
        private Long fileId;
        private int versionNumber;
        private String uploadedBy;
        private String checksum;
        private long sizeBytes;
        private Instant uploadedAt;

        public FileVersionResponseBuilder id(Long id) { this.id = id; return this; }
        public FileVersionResponseBuilder fileId(Long fileId) { this.fileId = fileId; return this; }
        public FileVersionResponseBuilder versionNumber(int versionNumber) { this.versionNumber = versionNumber; return this; }
        public FileVersionResponseBuilder uploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; return this; }
        public FileVersionResponseBuilder checksum(String checksum) { this.checksum = checksum; return this; }
        public FileVersionResponseBuilder sizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; return this; }
        public FileVersionResponseBuilder uploadedAt(Instant uploadedAt) { this.uploadedAt = uploadedAt; return this; }

        public FileVersionResponse build() {
            return new FileVersionResponse(id, fileId, versionNumber, uploadedBy, checksum, sizeBytes, uploadedAt);
        }
    }
}
