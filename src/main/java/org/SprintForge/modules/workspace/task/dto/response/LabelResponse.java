package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelResponse {
    private Long id;
    private Long projectId;
    private String name;
    private String color;
    private String description;
    private Boolean archived;
    private String createdBy;
    private LocalDateTime createdAt;
}