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

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Column(name = "storage_key")
    private String storageKey;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "checksum")
    private String checksum;

    @Column(name = "is_archived", nullable = false)
    private Boolean archived = false;
}
