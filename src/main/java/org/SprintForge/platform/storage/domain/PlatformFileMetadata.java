package org.SprintForge.platform.storage.domain;

import jakarta.persistence.*;

@Entity(name = "PlatformFileMetadata")
@Table(name = "platform_file_metadata")
public class PlatformFileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stored_file_id", nullable = false)
    private PlatformStoredFile storedFile;

    @Column(name = "meta_key", nullable = false)
    private String metaKey;

    @Column(name = "meta_value", columnDefinition = "TEXT")
    private String metaValue;

    public PlatformFileMetadata() {}

    public PlatformFileMetadata(Long id, PlatformStoredFile storedFile, String metaKey, String metaValue) {
        this.id = id;
        this.storedFile = storedFile;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
    }

    public static PlatformFileMetadataBuilder builder() {
        return new PlatformFileMetadataBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PlatformStoredFile getStoredFile() { return storedFile; }
    public void setStoredFile(PlatformStoredFile storedFile) { this.storedFile = storedFile; }

    public String getMetaKey() { return metaKey; }
    public void setMetaKey(String metaKey) { this.metaKey = metaKey; }

    public String getMetaValue() { return metaValue; }
    public void setMetaValue(String metaValue) { this.metaValue = metaValue; }

    public static class PlatformFileMetadataBuilder {
        private Long id;
        private PlatformStoredFile storedFile;
        private String metaKey;
        private String metaValue;

        public PlatformFileMetadataBuilder id(Long id) { this.id = id; return this; }
        public PlatformFileMetadataBuilder storedFile(PlatformStoredFile storedFile) { this.storedFile = storedFile; return this; }
        public PlatformFileMetadataBuilder metaKey(String metaKey) { this.metaKey = metaKey; return this; }
        public PlatformFileMetadataBuilder metaValue(String metaValue) { this.metaValue = metaValue; return this; }

        public PlatformFileMetadata build() {
            return new PlatformFileMetadata(id, storedFile, metaKey, metaValue);
        }
    }
}
