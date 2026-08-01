package org.SprintForge.modules.workspace.task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskTemplateRequest {

    private String name;

    private String description;

    private Long defaultStatusId;

    private Long defaultPriorityId;

    private Double estimatedHours;

    private Integer storyPoints;

    private Boolean isPublic;

    private List<CreateTaskTemplateRequest.ChecklistDTO> checklists;

    private List<CreateTaskTemplateRequest.LabelDTO> labels;

    private List<CreateTaskTemplateRequest.AttachmentDTO> attachments;

    private List<CreateTaskTemplateRequest.CustomFieldDTO> customFields;

    private List<Long> watcherUserIds;
}
