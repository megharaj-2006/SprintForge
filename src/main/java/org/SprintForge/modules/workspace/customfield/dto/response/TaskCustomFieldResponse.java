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
public class TaskCustomFieldResponse {
    private Long fieldId;
    private String fieldName;
    private CustomFieldType fieldType;
    private Boolean required;
    private String defaultValue;
    private String options;
    private String validationRules;
    private Long valueId;
    private String value;
}
