package org.SprintForge.modules.workspace.project.governance.approval.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecideApprovalRequest {

    private String comments;
}
