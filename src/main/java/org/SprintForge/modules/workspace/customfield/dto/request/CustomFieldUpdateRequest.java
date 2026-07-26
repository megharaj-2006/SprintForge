package org.SprintForge.modules.workspace.customfield.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.customfield.entity.enums.CustomFieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldUpdateRequest {

    @Size(min = 2, max = 100, message = "Field name must be between 2 and 100 characters")
    private String name;

    private String description;
    private CustomFieldType type;
    private String options;
    private Boolean required;
    private String defaultValue;
}
