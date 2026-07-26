package org.SprintForge.modules.workspace.automation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutomationRuleCreateRequest {

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;

    @NotBlank(message = "Rule name is required")
    @Size(min = 2, max = 150, message = "Rule name must be between 2 and 150 characters")
    private String name;

    private String description;
    private String triggerType;
    private String triggerConfiguration;
    private String conditionConfiguration;
    private String actionConfiguration;
    private Integer executionOrder;
    private Boolean enabled;
    private Long createdByUserId;
}
