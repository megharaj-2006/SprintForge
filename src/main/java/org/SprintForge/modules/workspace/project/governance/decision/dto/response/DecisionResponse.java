package org.SprintForge.modules.workspace.project.governance.decision.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.decision.entity.enums.DecisionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecisionResponse {

    private Long id;
    private Long projectId;
    private String title;
    private String problemStatement;
    private String decision;
    private String alternatives;
    private String reasoning;
    private String impact;
    private DecisionStatus status;
    private LocalDate decisionDate;
    private Long ownerId;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
