package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskTemplateRequest {

    private Long workspaceId;

    private Long projectId;

    @NotBlank(message = "Template name is required")
    private String name;

    private String description;

    private Long defaultStatusId;

    private Long defaultPriorityId;

    private Double estimatedHours;

    private Integer storyPoints;

    private Boolean isPublic;

    private List<ChecklistDTO> checklists;

    private List<LabelDTO> labels;

    private List<AttachmentDTO> attachments;

    private List<CustomFieldDTO> customFields;

    private List<Long> watcherUserIds;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChecklistDTO {
        private String title;
        private Integer position;
        private List<ChecklistItemDTO> items;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChecklistItemDTO {
        private String title;
        private String description;
        private Integer position;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LabelDTO {
        private Long labelId;
        private String name;
        private String color;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttachmentDTO {
        private String fileName;
        private String fileUrl;
        private Long fileSize;
        private String fileType;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomFieldDTO {
        private Long customFieldId;
        private String fieldName;
        private String fieldValue;
    }
}
