package org.SprintForge.platform.storage.dto;

public class FileMetadataResponse {
    private Long id;
    private Long fileId;
    private String metaKey;
    private String metaValue;

    public FileMetadataResponse() {}

    public FileMetadataResponse(Long id, Long fileId, String metaKey, String metaValue) {
        this.id = id;
        this.fileId = fileId;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
    }

    public static FileMetadataResponseBuilder builder() {
        return new FileMetadataResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFileId() { return fileId; }
    public void setFileId(Long fileId) { this.fileId = fileId; }

    public String getMetaKey() { return metaKey; }
    public void setMetaKey(String metaKey) { this.metaKey = metaKey; }

    public String getMetaValue() { return metaValue; }
    public void setMetaValue(String metaValue) { this.metaValue = metaValue; }

    public static class FileMetadataResponseBuilder {
        private Long id;
        private Long fileId;
        private String metaKey;
        private String metaValue;

        public FileMetadataResponseBuilder id(Long id) { this.id = id; return this; }
        public FileMetadataResponseBuilder fileId(Long fileId) { this.fileId = fileId; return this; }
        public FileMetadataResponseBuilder metaKey(String metaKey) { this.metaKey = metaKey; return this; }
        public FileMetadataResponseBuilder metaValue(String metaValue) { this.metaValue = metaValue; return this; }

        public FileMetadataResponse build() {
            return new FileMetadataResponse(id, fileId, metaKey, metaValue);
        }
    }
}
