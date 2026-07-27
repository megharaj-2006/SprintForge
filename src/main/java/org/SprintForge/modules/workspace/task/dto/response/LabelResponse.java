package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.modules.workspace.task.entity.TaskLabel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelResponse {

    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String color;
    private boolean archived;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public static LabelResponse fromEntity(TaskLabel label) {
        LabelResponse response = new LabelResponse();
        response.setId(label.getId());
        response.setProjectId(label.getProject().getId());
        response.setName(label.getName());
        response.setDescription(label.getDescription());
        response.setColor(label.getColor());
        response.setArchived(label.isArchived());
        response.setCreatedBy(label.getCreatedBy());
        response.setCreatedAt(label.getCreatedAt());
        response.setUpdatedBy(label.getUpdatedBy());
        response.setUpdatedAt(label.getUpdatedAt());
        return response;
    }
}