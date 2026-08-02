package org.SprintForge.modules.workspace.project.governance.document.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.document.entity.enums.DocumentFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDocumentRequest {

    @Size(min = 2, max = 150, message = "Document title must be between 2 and 150 characters")
    private String title;

    private Long folderId;
    private String content;
    private DocumentFormat format;
    private String changeSummary;
    private Boolean isPinned;
    private Boolean isFavorite;
    private Boolean isArchived;
}
