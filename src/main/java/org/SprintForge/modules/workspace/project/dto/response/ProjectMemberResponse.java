package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberResponse {

    private Long id;
    private Long projectId;
    private Long userId;
    private String userName;
    private String userEmail;
    private String avatarUrl;
    private Long roleId;
    private String roleName;
    private LocalDateTime joinedAt;
}
