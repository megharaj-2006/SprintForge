package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberSummaryResponse {

    private Long id;
    private Long userId;
    private String userName;
    private String avatarUrl;
    private String roleName;
    private WorkspaceMemberStatus status;
}
