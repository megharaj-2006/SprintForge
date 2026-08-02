package org.SprintForge.modules.workspace.project.governance.decision.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDecisionRequest {

    @NotBlank(message = "Decision title is required")
    @Size(min = 2, max = 150, message = "Decision title must be between 2 and 150 characters")
    private String title;

    private String problemStatement;
    private String decision;
    private String alternatives;
    private String reasoning;
    private String impact;
    private Long ownerId;
    private LocalDate decisionDate;
}
