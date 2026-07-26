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
    private Long workspaceId;
    private String name;
    private String description;
    private CustomFieldType type;
    private String options;
    private Boolean required;
    private String defaultValue;
    private Long createdByUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
