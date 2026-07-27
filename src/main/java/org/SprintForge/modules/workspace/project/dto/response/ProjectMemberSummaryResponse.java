package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberSummaryResponse {

    private Long id;
    private Long projectId;
    private Long workspaceMemberId;
    private Long userId;
    private String userName;
    private String roleName;
    private ProjectMemberStatus status;
    private LocalDateTime joinedAt;
}
