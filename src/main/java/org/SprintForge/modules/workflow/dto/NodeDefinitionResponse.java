package org.SprintForge.modules.workflow.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NodeDefinitionResponse {
    private Long id;
    private String nodeKey;
    private String displayName;
    private String category;
    private String configurationSchema;
    private String outputSchema;
    private String definitionVersion;
    private Boolean enabled;
    private LocalDateTime createdAt;
}
