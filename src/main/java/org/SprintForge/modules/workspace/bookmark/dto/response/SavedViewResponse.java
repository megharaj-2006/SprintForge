package org.SprintForge.modules.workspace.bookmark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedViewResponse {

    private Long id;
    private Long workspaceId;
    private Long projectId;
    private Long userId;
    private String name;
    private String description;
    private String viewType;
    private String filters;
    private String sorting;
    private String grouping;
    private String columns;
    private String layout;
    private String visibility;
    private Boolean isDefault;
    private Boolean isShared;
    private Boolean isFavorite;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
