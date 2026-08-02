package org.SprintForge.modules.workspace.project.governance.decision.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.decision.entity.enums.DecisionStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDecisionRequest {

    @Size(min = 2, max = 150, message = "Decision title must be between 2 and 150 characters")
    private String title;

    private String problemStatement;
    private String decision;
    private String alternatives;
    private String reasoning;
    private String impact;
    private DecisionStatus status;
    private Long ownerId;
    private LocalDate decisionDate;
}
