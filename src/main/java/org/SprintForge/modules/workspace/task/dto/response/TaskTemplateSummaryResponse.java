package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplateSummaryResponse {

    private Long id;
    private Long workspaceId;
    private Long projectId;
    private String name;
    private String description;
    private Boolean isPublic;
    private Boolean isArchived;
    private Integer usageCount;
    private Integer favoritedCount;
    private Boolean isFavoritedByCurrentUser;
    private LocalDateTime createdAt;
}
