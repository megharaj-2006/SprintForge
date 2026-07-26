package org.SprintForge.modules.workspace.ai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AISuggestionResponse {

    private Long id;
    private Long workspaceId;
    private String entityType;
    private Long entityId;
    private String suggestionType;
    private String content;
    private Boolean accepted;
    private Long acceptedBy;
    private LocalDateTime acceptedAt;
    private LocalDateTime createdAt;
}
