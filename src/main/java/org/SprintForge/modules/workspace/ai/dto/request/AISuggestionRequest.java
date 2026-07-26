package org.SprintForge.modules.workspace.ai.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AISuggestionRequest {

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;

    private String entityType;
    private Long entityId;
    private String suggestionType;
    private String prompt;
}
