package org.SprintForge.modules.workflow.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkflowNodeResponse {
    private Long id;
    private Long definitionId;
    private String definitionNodeKey;
    private String name;
    private String nodeType;
    private Double positionX;
    private Double positionY;
    private Boolean enabled;
    private LocalDateTime createdAt;
}
