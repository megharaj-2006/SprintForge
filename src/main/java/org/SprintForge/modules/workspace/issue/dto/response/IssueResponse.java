package org.SprintForge.modules.workspace.issue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {

    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private Long assigneeId;
    private Long reporterId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
