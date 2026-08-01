package org.SprintForge.modules.workspace.attachment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponse {
    private Long id;
    private Long workspaceId;
    private Long taskId;
    private Long uploadedBy;
    private String fileName;
    private String originalFileName;
    private String contentType;
    private Long size;
    private String storageKey;
    private String downloadUrl;
    private String checksum;
    private Boolean archived;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
