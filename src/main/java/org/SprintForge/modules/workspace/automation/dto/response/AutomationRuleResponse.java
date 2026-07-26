package org.SprintForge.modules.workspace.automation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutomationRuleResponse {

    private Long id;
    private Long workspaceId;
    private String name;
    private String description;
    private String triggerType;
    private String triggerConfiguration;
    private String conditionConfiguration;
    private String actionConfiguration;
    private Integer executionOrder;
    private Boolean enabled;
    private Long createdByUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
