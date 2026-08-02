package org.SprintForge.modules.workspace.project.governance.document.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersionResponse {

    private Long id;
    private Long documentId;
    private Integer versionNumber;
    private String content;
    private String changeSummary;
    private Long createdBy;
    private LocalDateTime createdAt;
}
