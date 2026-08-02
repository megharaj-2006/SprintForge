package org.SprintForge.modules.workspace.project.governance.change.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeStatus;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateChangeRequest {

    @Size(min = 2, max = 150, message = "Change title must be between 2 and 150 characters")
    private String title;

    private ChangeType changeType;
    private String description;
    private String reason;
    private String impact;
    private ChangeStatus status;
    private Long approvedById;
    private Long implementedById;
}
