package org.SprintForge.modules.workspace.project.governance.risk.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskCategory;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskImpact;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskProbability;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRiskRequest {

    @NotBlank(message = "Risk title is required")
    @Size(min = 2, max = 150, message = "Risk title must be between 2 and 150 characters")
    private String title;

    private String description;
    private RiskCategory category;
    private RiskProbability probability;
    private RiskImpact impact;
    private Long ownerId;
    private LocalDate targetMitigationDate;
    private String mitigationPlan;
    private String contingencyPlan;
    private String triggerConditions;
}
