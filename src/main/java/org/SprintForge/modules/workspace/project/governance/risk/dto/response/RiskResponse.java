package org.SprintForge.modules.workspace.project.governance.risk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskCategory;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskImpact;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskProbability;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskResponse {

    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private RiskCategory category;
    private RiskStatus status;
    private RiskProbability probability;
    private RiskImpact impact;
    private String severity;
    private Integer riskScore;
    private Long ownerId;
    private LocalDate identifiedDate;
    private LocalDate targetMitigationDate;
    private LocalDateTime resolvedDate;
    private String mitigationPlan;
    private String contingencyPlan;
    private String triggerConditions;
    private Boolean isArchived;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
