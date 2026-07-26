package org.SprintForge.modules.workspace.form.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDetailResponse {

    private Long id;
    private Long workspaceId;
    private String title;
    private String description;
    private Boolean isPublic;
    private Boolean allowAnonymousSubmission;
    private Long createdByUserId;
    private String createdByUserName;
    private Integer fieldCount;
    private Integer submissionCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
