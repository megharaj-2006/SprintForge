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
public class IssueDetailResponse {

    private Long id;
    private Long projectId;
    private String projectName;
    private String title;
    private String description;
    private String priority;
    private String status;
    private Long assigneeId;
    private String assigneeName;
    private Long reporterId;
    private String reporterName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
