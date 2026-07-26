package org.SprintForge.modules.workspace.wiki.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiPageCreateRequest {

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;

    private Long projectId;
    private Long parentPageId;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    private String slug;
    private String content;
    private Long createdByUserId;
}
