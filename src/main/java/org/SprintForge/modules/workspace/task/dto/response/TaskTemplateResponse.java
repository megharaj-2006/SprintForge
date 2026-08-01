package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplateResponse {

    private Long id;
    private Long workspaceId;
    private Long projectId;
    private String name;
    private String description;
    private Long defaultStatusId;
    private Long defaultPriorityId;
    private Double estimatedHours;
    private Integer storyPoints;
    private Long createdByUserId;
    private Boolean isPublic;
    private Boolean isArchived;
    private Integer usageCount;
    private Integer favoritedCount;
    private Boolean isFavoritedByCurrentUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ChecklistResponse> checklists;
    private List<LabelResponse> labels;
    private List<AttachmentResponse> attachments;
    private List<CustomFieldResponse> customFields;
    private List<Long> watcherUserIds;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChecklistResponse {
        private Long id;
        private String title;
        private Integer position;
        private List<ChecklistItemResponse> items;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChecklistItemResponse {
        private Long id;
        private String title;
        private String description;
        private Integer position;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LabelResponse {
        private Long id;
        private Long labelId;
        private String name;
        private String color;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttachmentResponse {
        private Long id;
        private String fileName;
        private String fileUrl;
        private Long fileSize;
        private String fileType;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomFieldResponse {
        private Long id;
        private Long customFieldId;
        private String fieldName;
        private String fieldValue;
    }
}
