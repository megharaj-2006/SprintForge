package org.SprintForge.modules.workspace.wiki.dto.response;

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
public class WikiPageDetailResponse {

    private Long id;
    private Long workspaceId;
    private Long projectId;
    private String projectName;
    private Long parentPageId;
    private String parentPageTitle;
    private String title;
    private String slug;
    private String content;
    private Integer pageVersion;
    private Long createdByUserId;
    private String createdByUserName;
    private Long updatedByUserId;
    private String updatedByUserName;
    private List<WikiPageSummaryResponse> childPages;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
