package org.SprintForge.modules.workspace.bookmark.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSavedViewRequest {

    private Long workspaceId;

    private Long projectId;

    @NotBlank(message = "View name is required")
    private String name;

    private String description;

    private String viewType;

    private String filters;

    private String sorting;

    private String grouping;

    private String columns;

    private String layout;

    private String visibility; // PRIVATE, WORKSPACE, PROJECT

    private Boolean isDefault;

    private Boolean isShared;
}
