package org.SprintForge.modules.workspace.customfield.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.customfield.entity.enums.CustomFieldType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldResponse {

    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private CustomFieldType fieldType;
    private String options;
    private Boolean required;
    private String defaultValue;
    private Integer position;
    private Boolean archived;
    private String validationRules;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
