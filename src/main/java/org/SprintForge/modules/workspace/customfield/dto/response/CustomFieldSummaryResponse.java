package org.SprintForge.modules.workspace.customfield.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.customfield.entity.enums.CustomFieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldSummaryResponse {
    private Long id;
    private String name;
    private CustomFieldType fieldType;
    private Boolean required;
}
