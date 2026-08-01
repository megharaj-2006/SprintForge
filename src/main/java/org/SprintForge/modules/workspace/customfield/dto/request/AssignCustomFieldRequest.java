package org.SprintForge.modules.workspace.customfield.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignCustomFieldRequest {

    @NotNull(message = "Custom Field ID is required")
    private Long customFieldId;

    private String value;
}
