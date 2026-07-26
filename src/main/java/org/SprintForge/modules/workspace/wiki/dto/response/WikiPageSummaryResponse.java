package org.SprintForge.modules.workspace.wiki.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiPageSummaryResponse {

    private Long id;
    private Long parentPageId;
    private String title;
    private String slug;
    private Integer pageVersion;
}
