package org.SprintForge.modules.workspace.workspace.department.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    private Long id;
    private Long workspaceId;
    private String name;
    private String description;
    private Long headMemberId;
    private String color;
    private boolean isArchived;
    private Long teamCount;
    private Long memberCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
