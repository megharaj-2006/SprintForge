package org.SprintForge.modules.workspace.project.governance.document.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.document.entity.enums.DocumentFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {

    private Long id;
    private Long projectId;
    private Long folderId;
    private String title;
    private String slug;
    private String content;
    private DocumentFormat format;
    private Integer versionNumber;
    private Long authorId;
    private Long lastEditorId;
    private Boolean isPinned;
    private Boolean isFavorite;
    private Boolean isArchived;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
