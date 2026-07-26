package org.SprintForge.modules.workspace.attachment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends SoftDeleteEntity {

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "uploaded_by")
    private Long uploadedBy;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "storage_provider")
    private String storageProvider;

    @Column(name = "storage_path")
    private String storagePath;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "checksum")
    private String checksum;
}

