package org.SprintForge.modules.workspace.wiki.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiPageResponse {

    private Long id;
    private Long workspaceId;
    private Long projectId;
    private Long parentPageId;
    private String title;
    private String slug;
    private String content;
    private Integer pageVersion;
    private Long createdByUserId;
    private Long updatedByUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
